package com.example.backend.Controller.Services;

import com.example.backend.Model.Entities.User;
import com.example.backend.Model.Repositories.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String email, String password, HttpSession session) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("userEmail", email);
                session.setAttribute("userRole", user.getRole());
                return user;
            }
        }
        return null;
    }
}
