package com.carelink.Manos.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {

    private String jwtSecret = "your_super_secret_key"; // Should be in application.properties
    private int jwtExpirationMs = 3600000; // 1 hour

    // Method to generate JWT token
    public String generateToken(String username) {
        long now = System.currentTimeMillis();
        Date expirationDate = new Date(now + jwtExpirationMs);

        // Header (Base64 encoded)
        String header = "{\"alg\": \"HS256\", \"typ\": \"JWT\"}";
        String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(header.getBytes());

        // Payload (Base64 encoded)
        String payload = "{\"sub\": \"" + username + "\", \"exp\": " + expirationDate.getTime() / 1000 + "}";
        String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes());

        // Signature
        String signatureInput = encodedHeader + "." + encodedPayload;
        String signature = createSignature(signatureInput);

        // JWT token: header.payload.signature
        return encodedHeader + "." + encodedPayload + "." + signature;
    }

    // Method to create signature using HMAC SHA-256
    private String createSignature(String input) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] signatureBytes = mac.doFinal(input.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error creating signature", e);
        }
    }

    // Method to get username from JWT token
    public String getUsernameFromToken(String token) {
        String[] parts = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        String username = payload.split("\"sub\":\"")[1].split("\"")[0];
        return username;
    }

    // Method to validate JWT token
    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }

            // Extract parts
            String header = parts[0];
            String payload = parts[1];
            String signature = parts[2];

            // Recalculate the signature
            String signatureInput = header + "." + payload;
            String recalculatedSignature = createSignature(signatureInput);

            // Compare with the provided signature
            return recalculatedSignature.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }

    // Method to check if the token is expired
    public boolean isTokenExpired(String token) {
        String[] parts = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        long expirationTime = Long.parseLong(payload.split("\"exp\":")[1].split("}")[0]);
        return expirationTime < System.currentTimeMillis() / 1000;
    }
}
