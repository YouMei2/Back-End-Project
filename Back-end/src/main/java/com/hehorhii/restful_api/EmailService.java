package com.hehorhii.restful_api;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// EmailService handles sending emails for verification and other purposes.
// This service uses JavaMailSender to send simple text emails.
@Service
public class EmailService {
    private final JavaMailSender mailSender;

    // Constructor injecting JavaMailSender dependency
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Sends a verification code to the specified email address
    public void sendVerificationCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Life Wheel web-site");
        message.setText("your verification code: " + code);
        mailSender.send(message);
    }
}
