package com.hehorhii.restful_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @PostMapping
    public User RegisterUser(@RequestBody User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("user already exists");
        }
        return userRepository.save(user);
    }
}
