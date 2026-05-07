package com.hehorhii.restful_api;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wheel")
@CrossOrigin(origins = "http://localhost:63342")
// WheelOfBalanceController handles wheel of life balance operations.
// This REST controller provides endpoints for retrieving and updating user balance assessments.
public class WheelOfBalanceController {
    private final WheelOfBalanceRepository wheelRepository;

    // Constructor injecting WheelOfBalanceRepository dependency
    public WheelOfBalanceController(WheelOfBalanceRepository wheelRepository) {
        this.wheelRepository = wheelRepository;
    }

    // Get the wheel of balance for a specific user
    @GetMapping("/{userId}")
    public WheelOfBalance getWheel(@PathVariable(name = "userId") Long userId) {
        return wheelRepository.findByUserId(userId)
                .orElse(new WheelOfBalance());
    }

    // Save or update the wheel of balance for a specific user
    @PostMapping("/{userId}")
    public WheelOfBalance saveWheel(@PathVariable(name = "userId") Long userId, @RequestBody WheelOfBalance newWheel) {
        return wheelRepository.findByUserId(userId)
                .map(wheel -> {
                    wheel.setHealth(newWheel.getHealth());
                    wheel.setFamily(newWheel.getFamily());
                    wheel.setWork(newWheel.getWork());
                    wheel.setFinance(newWheel.getFinance());
                    wheel.setLearning(newWheel.getLearning());
                    wheel.setRest(newWheel.getRest());
                    wheel.setFriends(newWheel.getFriends());
                    wheel.setSpiritual(newWheel.getSpiritual());
                    return wheelRepository.save(wheel);
                })
                .orElseGet(() -> {
                    newWheel.setUserId(userId);
                    return wheelRepository.save(newWheel);
                });
    }
}
