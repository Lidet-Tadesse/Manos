package com.carelink.Manos.service;

import com.carelink.Manos.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User signup(User signupData) {
        // Perform the signup logic with User entity data (e.g., save to DB)
        return signupData; // Return the saved User entity
    }

    public User login(User loginRequest) {
        // Perform login logic using the User entity (check credentials, etc.)
        return loginRequest; // Return the User entity for now, for simplicity
    }

    public String deleteAccount(String id) {
        // Delete user account logic based on the id
        return "Account deleted"; // Dummy response
    }
}
