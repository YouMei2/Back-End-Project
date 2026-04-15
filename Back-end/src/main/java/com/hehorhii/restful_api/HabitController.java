package com.hehorhii.restful_api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/habits")
public class HabitController {
    private final HabitsRepository habitsRepository;

    public HabitController(HabitsRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @GetMapping
    public List<Habits> getAllHabits(@RequestParam("userId") Long userId) { // Добавили ("userId")
        return habitsRepository.findByUserId(userId);
    }

    @PostMapping
    public Habits createHabit(@RequestBody Habits habits) {
        if (habits.getStreak() == null) habits.setStreak(0L);
        return habitsRepository.save(habits);
    }

    @PutMapping("/{id}")
    public Habits updateHabit(@PathVariable("id") Long id, @RequestBody Habits updatedData) { // Добавили ("id")
        Habits habit = habitsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habit not found with id: " + id));

        if (updatedData.getStreak() != null) {
            habit.setStreak(updatedData.getStreak());
        }

        if (updatedData.getCurrent_days() != null) {
            habit.setCurrent_days(updatedData.getCurrent_days());
        }

        return habitsRepository.save(habit);
    }

    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable("id") Long id) { // Добавили ("id")
        habitsRepository.deleteById(id);
    }
}


