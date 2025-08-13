package com.example.bankcards.service.user;

import com.example.bankcards.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Сервис для получения текущего аутентифицированного пользователя из контекста безопасности.
 */
@Service
public class UserContextService {
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }
        return null;
    }
}
