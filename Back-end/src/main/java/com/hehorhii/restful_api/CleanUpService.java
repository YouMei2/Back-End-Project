package com.hehorhii.restful_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CleanUpService {
    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 60000)
    public void clearExpiredCodes() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);

        // Находим всех юзеров, у которых есть код и он был создан более 10 минут назад
        List<User> expiredUsers = userRepository.findByCodeIsNotNullAndCodeCreatedAtBefore(tenMinutesAgo);

        for (User user : expiredUsers) {
            user.setCode(null);
            user.setCodeCreatedAt(null);
            userRepository.save(user);
            System.out.println("Code for user " + user.getEmail() + " Confirmation code expired and removed.");
        }
    }
}
