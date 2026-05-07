package com.hehorhii.restful_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// CleanUpService handles scheduled cleanup tasks for expired verification codes.
// This service runs periodically to remove expired codes from user accounts.
@Service
public class CleanUpService {
    @Autowired
    private UserRepository userRepository;

    // Scheduled method to clear expired verification codes every minute
    @Scheduled(fixedRate = 60000)
    public void clearExpiredCodes() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);

        // Find all users with a code created more than 10 minutes ago
        List<User> expiredUsers = userRepository.findByCodeIsNotNullAndCodeCreatedAtBefore(tenMinutesAgo);

        for (User user : expiredUsers) {
            user.setCode(null);
            user.setCodeCreatedAt(null);
            userRepository.save(user);
            System.out.println("Code for user " + user.getEmail() + " Confirmation code expired and removed.");
        }
    }
}
