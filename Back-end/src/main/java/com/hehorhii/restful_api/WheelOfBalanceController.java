package com.hehorhii.restful_api;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wheel")
@CrossOrigin(origins = "*")
public class WheelOfBalanceController {
    private final WheelOfBalanceRepository wheelRepository;

    public WheelOfBalanceController(WheelOfBalanceRepository wheelRepository) {
        this.wheelRepository = wheelRepository;
    }

    @GetMapping("/{userId}")
    public WheelOfBalance getWheel(@PathVariable(name = "userId") Long userId) {
        return wheelRepository.findByUserId(userId)
                .orElse(new WheelOfBalance());
    }

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
