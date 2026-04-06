package com.hehorhii.restful_api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserController(UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User RegisterUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String verificationCode = "123456";
        user.setCode(verificationCode);
        user.setEnabled(false);

        try {
            emailService.sendVerificationCode(user.getEmail(), verificationCode);
        } catch (Exception e) {
            System.out.println("Email error: " + e.getMessage());
        }
        return userRepository.save(user);
    }
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        return userRepository.findByEmail(loginData.getEmail())
                .map(user -> {
                    if (!user.isEnabled()) {
                        return ResponseEntity.status(401).body("Please verify your email first!");
                    }
                    if (passwordEncoder.matches(loginData.getPassword(), user.getPassword())) {
                        return ResponseEntity.ok(user); // Возвращаем юзера, если пароль совпал
                    }
                    return ResponseEntity.status(401).body("Invalid password!");
                })
                .orElse(ResponseEntity.status(404).body("User not found!"));
    }
}

