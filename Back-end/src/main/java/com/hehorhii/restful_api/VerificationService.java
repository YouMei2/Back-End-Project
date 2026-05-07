package com.hehorhii.restful_api;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

// VerificationService handles verification code generation for user accounts.
// This service provides methods to generate secure random codes for email verification.
@Service
public class VerificationService {
    private final EmailService emailService;

    // Constructor injecting EmailService dependency
    public VerificationService(EmailService emailService){
        this.emailService = emailService;
    }

    private static final SecureRandom secureRandom = new SecureRandom();

    // Generates a 6-digit verification code
    public static String generateCode(){
        int code = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(code);
    }
}
