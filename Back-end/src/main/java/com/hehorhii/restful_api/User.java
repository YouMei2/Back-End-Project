package com.hehorhii.restful_api;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user;
    private String email;
    private String password;
    private String code;
    private boolean enabled = false;
    @Column(name = "code_created_at", columnDefinition = "DATETIME")
    private LocalDateTime codeCreatedAt;

    // constructor
    public User() {}

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getUser() {
        return user;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setCodeCreatedAt(LocalDateTime codeCreatedAt) {
        this.codeCreatedAt = codeCreatedAt;
    }
    public LocalDateTime getCodeCreatedAt() {
        return codeCreatedAt;
    }
}
