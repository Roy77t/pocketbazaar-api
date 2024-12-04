package com.pocketbazaar.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pocketbazaar.api.model.User;
import com.pocketbazaar.api.service.UserService;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to fetch all users
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();  // Fetch all users from the service
    }
}