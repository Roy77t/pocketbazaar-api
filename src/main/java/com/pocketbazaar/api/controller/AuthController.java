// package com.pocketbazaar.api.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import com.pocketbazaar.api.model.User;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     @Autowired
//     private com.pocketbazaar.api.service.UserService userService;

//     @PostMapping("/signup")
//     public String signup(@RequestBody User user) {
//         userService.registerUser(user);
//         return "User registered successfully!";
//     }

//     @PostMapping("/login")
//     public String login(@RequestBody User user) {
//         // Login logic here (for now, just return a success message)
//         return "Login successful!";
//     }
// }

package com.pocketbazaar.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.pocketbazaar.api.model.User;
import com.pocketbazaar.api.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        // Register the user by calling the service method which encrypts the password
        userService.registerUser(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // Find the user by email from the database
        User existingUser = userService.findByEmail(user.getEmail());

        // If user not found
        if (existingUser == null) {
            return "User not found!";
        }

        // If password does not match
        if (!userService.validatePassword(user.getPassword(), existingUser.getPassword())) {
            return "Invalid email or password!";
        }

        // If both email and password match
        return "Login successful!";
    }
}
