package com.hehorhii.restful_api;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryRepository diaryRepository;

    public DiaryController(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    // Получить все записи конкретного пользователя
    @GetMapping
    public List<Diary> getUserDiary(@RequestParam(name = "userId") Long userId) {
        return diaryRepository.findByUserId(userId);
    }

    // Создать новую запись
    @PostMapping
    public Diary createEntry(@RequestBody Diary diary) {
        return diaryRepository.save(diary);
    }

    // Редактировать существующую запись (по ID)
    @PutMapping("/{id}")
    public Diary updateEntry(@PathVariable("id") Long id, @RequestBody Diary updatedData) {
        Diary entry = diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));

        if (updatedData.getTitle() != null) entry.setTitle(updatedData.getTitle());
        if (updatedData.getMood() != null) entry.setMood(updatedData.getMood());
        if (updatedData.getContent() != null) entry.setContent(updatedData.getContent());

        return diaryRepository.save(entry);
    }

    // Удалить запись
    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable("id") Long id) {
        diaryRepository.deleteById(id);
    }
}