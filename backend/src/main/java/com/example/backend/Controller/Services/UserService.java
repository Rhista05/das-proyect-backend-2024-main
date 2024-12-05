package com.example.backend.Controller.Services;

import com.example.backend.Model.Entities.PasswordResetToken;
import com.example.backend.Model.Entities.User;
import com.example.backend.Model.Repositories.PasswordResetTokenRepository;
import com.example.backend.Model.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository tokenRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    // Get all users
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    // Create a new user
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Delete a user
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar usuario por correo electrónico
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
