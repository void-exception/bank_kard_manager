package com.example.bankcards.security.service;

import com.example.bankcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.bankcards.entity.User;

/**
 * Сервис для работы с пользователями при регистрации и поиске по email.
 */
@Service
public class SecurityService {
    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<String> register(String email, String name, String password) {
        if (findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с таким email уже существует");
        }
        User user = new User(name, email, passwordEncoder.encode(password));
        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно зарегестрирован");
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
