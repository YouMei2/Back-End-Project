package com.hehorhii.restful_api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

// UserController handles user-related operations such as registration, login, verification, and account management.
// This REST controller provides endpoints for user authentication and profile updates.
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // Constructor injecting dependencies
    public UserController(UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Register a new user
    @PostMapping(produces = "text/plain;charset=UTF-8") // Explicitly specify that we are sending TEXT
    public ResponseEntity<String> RegisterUser(@RequestBody User user) {
        var existingUserOpt = userRepository.findByEmail(user.getEmail());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (!existingUser.isEnabled()) {
                return ResponseEntity.status(409).body("verify_needed"); // Short marker
            }
            return ResponseEntity.status(400).body("user_exists");
        }

        // Logic for creating a NEW user
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String verificationCode = VerificationService.generateCode();
        user.setCode(verificationCode);
        user.setEnabled(false);
        user.setCodeCreatedAt(LocalDateTime.now()); // Useful for resend

        try {
            emailService.sendVerificationCode(user.getEmail(), verificationCode);
        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
        }

        userRepository.save(user);
        return ResponseEntity.ok("success");
    }

    // Verify user account with code
    @PostMapping("/verify")
    public String verifyUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getCode().equals(code)) {
            user.setEnabled(true);
            user.setCode(null);
            userRepository.save(user);
            return "Account verified successfully!";
        } else {
            throw new RuntimeException("Invalid verification code!");
        }
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        return userRepository.findByEmail(loginData.getEmail())
                .map(user -> {
                    if (!user.isEnabled()) {
                        return ResponseEntity.status(401).body("Please verify your email first!");
                    }
                    if (passwordEncoder.matches(loginData.getPassword(), user.getPassword())) {
                        return ResponseEntity.ok(user); // Return user if password matches
                    }
                    return ResponseEntity.status(401).body("Invalid password!");
                })
                .orElse(ResponseEntity.status(404).body("User not found!"));
    }

    // Resend verification code
    @PostMapping("/resend-code")
    public ResponseEntity<?> resendCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        return userRepository.findByEmail(email)
                .map(user -> {
                    if (user.isEnabled()) {
                        return ResponseEntity.badRequest().body("Account already verified!");
                    }

                    // Generate a new code (use your code generation logic)
                    String newCode = VerificationService.generateCode();
                    user.setCode(newCode);
                    user.setCodeCreatedAt(LocalDateTime.now());
                    userRepository.save(user);

                    // Send the email (use your service)
                    try {
                        emailService.sendVerificationCode(user.getEmail(), newCode);
                        return ResponseEntity.ok("New code sent to " + email);
                    } catch (Exception e) {
                        return ResponseEntity.status(500).body("Error sending email");
                    }
                })
                .orElse(ResponseEntity.status(404).body("User not found"));
    }

    // 1. Account deletion
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok("Account deleted");
                })
                .orElse(ResponseEntity.status(404).body("User not found"));
    }

    // 2. Name change
    @PutMapping("/{id}/update-name")
    public ResponseEntity<?> updateName(@PathVariable Long id, @RequestBody Map<String, String> request) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUser(request.get("newName"));
                    userRepository.save(user);
                    // To avoid type conflicts, return a string or explicitly the response body
                    return ResponseEntity.ok().body("Name updated successfully");
                }).orElse(ResponseEntity.status(404).body("User not found"));
    }

    // 3. Password change
    @PutMapping("/{id}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        return userRepository.findById(id)
                .map(user -> {
                    String oldPassword = request.get("oldPassword");
                    String newPassword = request.get("newPassword");

                    if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                        user.setPassword(passwordEncoder.encode(newPassword));
                        userRepository.save(user);
                        return ResponseEntity.ok().body("Password updated");
                    } else {
                        return ResponseEntity.status(400).body("Wrong old password");
                    }
                }).orElse(ResponseEntity.status(404).body("User not found"));
    }

    // 4. Email change (with re-verification)
    @PutMapping("/{id}/update-email")
    public ResponseEntity<?> updateEmail(@PathVariable Long id, @RequestBody Map<String, String> request) {
        return userRepository.findById(id)
                .map(user -> {
                    String newEmail = request.get("newEmail").toLowerCase();
                    if (userRepository.existsByEmail(newEmail)) {
                        return ResponseEntity.status(400).body("Email already taken");
                    }

                    String code = VerificationService.generateCode();
                    user.setEmail(newEmail);
                    user.setCode(code);
                    user.setEnabled(false); // Log out and require confirmation
                    userRepository.save(user);

                    try {
                        emailService.sendVerificationCode(newEmail, code);
                    } catch (Exception e) {
                        return ResponseEntity.status(500).body("Error sending code");
                    }

                    return ResponseEntity.ok("Verification code sent to new email");
                }).orElse(ResponseEntity.status(404).body("User not found"));
    }
}
