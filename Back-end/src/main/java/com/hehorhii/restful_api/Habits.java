package com.hehorhii.restful_api;

import jakarta.persistence.*;

/**
 * Habits entity representing a habit in the application.
 * This class maps to the habits table in the database.
 */
@Entity
public class Habits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String name;
    private Long streak;
    private String current_days;

    // Default constructor
    public Habits(){}

    // Setters for habit properties
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreak(Long streak) {
        this.streak = streak;
    }

    public void setCurrent_days(String current_days) {
        this.current_days = current_days;
    }

    // Getters for habit properties
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Long getStreak() {
        return streak;
    }

    public String getCurrent_days() {
        return current_days;
    }
}
