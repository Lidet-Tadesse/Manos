package com.carelink.Manos.controller;

import com.carelink.Manos.model.User; // Import the User entity class directly
import com.carelink.Manos.service.UserService;
import com.carelink.Manos.service.LogoutService;
import com.carelink.Manos.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final LogoutService logoutService;
    private final PasswordResetService passwordResetService;

    @Autowired
    public AuthController(UserService userService, LogoutService logoutService,
            PasswordResetService passwordResetService) {
        this.userService = userService;
        this.logoutService = logoutService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody User signupData) {
        return ResponseEntity.ok(userService.signup(signupData)); // Directly use User entity class
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest)); // Directly use User entity class
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id) {
        return ResponseEntity.ok(Map.of("message", userService.deleteAccount(id)));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        logoutService.logout(token);
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        passwordResetService.initiateReset(email);
        return ResponseEntity.ok(Map.of("message", "Reset email sent"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody User passwordResetData) {
        passwordResetService.completeReset(passwordResetData); // Use User entity for password reset data
        return ResponseEntity.ok(Map.of("message", "Password updated"));
    }
}
