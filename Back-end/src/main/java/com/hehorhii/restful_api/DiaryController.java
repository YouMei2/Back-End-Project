package com.hehorhii.restful_api;

import org.springframework.web.bind.annotation.*;
import java.util.List;

// DiaryController handles diary-related operations.
// This REST controller provides endpoints for managing user diary entries.
@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryRepository diaryRepository;

    // Constructor injecting DiaryRepository dependency
    public DiaryController(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    // Get all diary entries for a specific user
    @GetMapping
    public List<Diary> getUserDiary(@RequestParam(name = "userId") Long userId) {
        return diaryRepository.findByUserId(userId);
    }

    // Create a new diary entry
    @PostMapping
    public Diary createEntry(@RequestBody Diary diary) {
        return diaryRepository.save(diary);
    }

    // Update an existing diary entry by ID
    @PutMapping("/{id}")
    public Diary updateEntry(@PathVariable("id") Long id, @RequestBody Diary updatedData) {
        Diary entry = diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        if (updatedData.getTitle() != null) entry.setTitle(updatedData.getTitle());
        if (updatedData.getMood() != null) entry.setMood(updatedData.getMood());
        if (updatedData.getContent() != null) entry.setContent(updatedData.getContent());

        return diaryRepository.save(entry);
    }

    // Delete a diary entry
    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable("id") Long id) {
        diaryRepository.deleteById(id);
    }
}