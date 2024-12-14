package com.pocketbazaar.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.pocketbazaar.api.model.User;
import com.pocketbazaar.api.service.UserService;
import com.pocketbazaar.api.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (userService.isEmailTaken(user.getEmail())) {
            return "Email is already registered!";
        }
        userService.registerUser(user);
        return "User registered successfully!";
    }

    
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser == null) {
            return "User not found!";
        }

        if (!userService.validatePassword(user.getPassword(), existingUser.getPassword())) {
            return "Invalid email or password!";
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(existingUser.getEmail());

        // Save the token in the database
        existingUser.setToken(token);
        userService.saveUser(existingUser);

        return "Login successful! Token: " + token;
    }
}
