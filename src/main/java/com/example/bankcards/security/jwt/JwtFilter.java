package com.example.bankcards.security.jwt;

import java.io.IOException;
import java.util.Collections;

import com.example.bankcards.entity.User;
import com.example.bankcards.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Фильтр JWT.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final SecurityService userService;

    public JwtFilter(JwtService jwtService, SecurityService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtService.validateToken(token)) {
                String email = jwtService.extractEmail(token);
                User user = userService.findByEmail(email);
                if (user != null) {
                    String role = user.getRole();

                    // Создаем коллекцию прав доступа
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            user, null, Collections.singletonList(authority));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
