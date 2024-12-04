// package com.pocketbazaar.api.service;

// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import com.pocketbazaar.api.model.User;
// import com.pocketbazaar.api.repository.UserRepository;

// @Service
// public class UserService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     // User registration: encrypt the password before saving
//     public User registerUser(User user) {
//         user.setPassword(passwordEncoder.encode(user.getPassword()));
//         return userRepository.save(user);
//     }

//     // Password validation: compare raw password with hashed password
//     public boolean validatePassword(String rawPassword, String hashedPassword) {
//         return passwordEncoder.matches(rawPassword, hashedPassword);
//     }
// }




package com.pocketbazaar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pocketbazaar.api.model.User;
import com.pocketbazaar.api.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // User registration: Encrypt the password before saving
    public User registerUser(User user) {
        // Encrypt password before saving to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email) != null;
    }
    
    // Find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Password validation: Compare raw password with hashed password
    public boolean validatePassword(String rawPassword, String hashedPassword) {
        // Use PasswordEncoder to match the raw password with the stored hashed password
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }


   public boolean isValidUser(String email) {
    User user = userRepository.findByEmail(email);
    if (user == null) {
        System.out.println("User not found in database for email: " + email);
        return false;
    }
    if (!user.isActive()) {
        System.out.println("User is not active for email: " + email);
        return false;
    }
    return true;
}



public User saveUser(User user) {
    return userRepository.save(user);
}
    public List<User> getAllUsers() {
        return userRepository.findAll();  
    }
}