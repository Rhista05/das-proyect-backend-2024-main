package com.example.backend.Model.Entities;

public class LoginResponse {

    private Long id;
    private String fullName;
    private String lastName;
    private Role role;

    // Constructor
    public LoginResponse(Long id, String fullName, String lastName, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.lastName = lastName;
        this.role = role;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
