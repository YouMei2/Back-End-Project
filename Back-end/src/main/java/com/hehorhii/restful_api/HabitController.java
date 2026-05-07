package com.hehorhii.restful_api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

// HabitController handles habit-related operations.
// This REST controller provides endpoints for managing user habits.
@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/habits")
public class HabitController {
    private final HabitsRepository habitsRepository;

    // Constructor injecting HabitsRepository dependency
    public HabitController(HabitsRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    // Get all habits for a specific user
    @GetMapping
    public List<Habits> getAllHabits(@RequestParam("userId") Long userId) { // Added ("userId")
        return habitsRepository.findByUserId(userId);
    }

    // Create a new habit
    @PostMapping
    public Habits createHabit(@RequestBody Habits habits) {
        if (habits.getStreak() == null) habits.setStreak(0L);
        return habitsRepository.save(habits);
    }

    // Update an existing habit by ID
    @PutMapping("/{id}")
    public Habits updateHabit(@PathVariable("id") Long id, @RequestBody Habits updatedData) { // Added ("id")
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

    // Delete a habit by ID
    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable("id") Long id) { // Added ("id")
        habitsRepository.deleteById(id);
    }
}
