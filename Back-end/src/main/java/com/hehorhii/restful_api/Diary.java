package com.hehorhii.restful_api;

import jakarta.persistence.*;
import java.sql.Timestamp;

// Diary entity representing a diary entry in the application.
// This class maps to the diary table in the database.
@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String title;
    private String mood;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Timestamp created_at;

    // Getters and setters for diary entry properties
    public Long getId() {
        return id;
    }
    public Long getUserId() {
        return userId;
    }
    public String getTitle() {
        return title;
    }
    public String getMood() {
        return mood;
    }
    public String getContent() {
        return content;
    }
    public Timestamp getCreated_at() {
        return created_at;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setMood(String mood) {
        this.mood = mood;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
