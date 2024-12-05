package com.example.backend.Model.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne
    private User user;

    private LocalDateTime expirationDate;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationDate = LocalDateTime.now().plusHours(1);  // El token expira en 1 hora
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
