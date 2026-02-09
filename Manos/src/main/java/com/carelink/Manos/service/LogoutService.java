package com.carelink.Manos.service;

import org.springframework.stereotype.Service;

@Service
public class LogoutService {

    public void logout(String token) {
        // Invalidate the token logic
    }

    public boolean isTokenInvalid(String token) {
        // Logic to check if the token is invalid
        return false; // Dummy response
    }
}
