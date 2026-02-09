package com.carelink.Manos.service;

import com.carelink.Manos.model.User;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    public void initiateReset(String email) {
        // Initiate password reset process
    }

    public void completeReset(User resetData) {
        // Complete password reset process using the User entity (e.g., update password)
    }
}
