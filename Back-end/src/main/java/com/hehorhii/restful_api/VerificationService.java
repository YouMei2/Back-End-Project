package com.hehorhii.restful_api;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class VerificationService {
    private final EmailService emailService;
    public VerificationService(EmailService emailService){
        this.emailService = emailService;
    }
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateCode(){
        int code = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(code);
    }
}
