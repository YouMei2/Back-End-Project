package com.hehorhii.restful_api;

import jakarta.persistence.*;

@Entity
@Table(name = "Wheel_of_balance")

public class WheelOfBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    private int health = 0;
    private int family = 0;
    private int work = 0;
    private int finance = 0;
    private int learning = 0;
    private int rest = 0;
    private int friends = 0;
    private int spiritual = 0;

    public WheelOfBalance() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public int getHealth() {
        return health;
    }

    public int getFamily() {
        return family;
    }

    public int getWork() {
        return work;
    }

    public int getFinance() {
        return finance;
    }

    public int getLearning() {
        return learning;
    }

    public int getRest() {
        return rest;
    }

    public int getFriends() {
        return friends;
    }

    public int getSpiritual() {
        return spiritual;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setFamily(int family) {
        this.family = family;
    }

    public void setWork(int work) {
        this.work = work;
    }

    public void setFinance(int finance) {
        this.finance = finance;
    }

    public void setLearning(int learning) {
        this.learning = learning;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public void setFriends(int friends) {
        this.friends = friends;
    }

    public void setSpiritual(int spiritual) {
        this.spiritual = spiritual;
    }
}
