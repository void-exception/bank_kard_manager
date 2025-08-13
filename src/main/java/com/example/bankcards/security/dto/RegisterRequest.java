package com.example.bankcards.security.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Сущность регестрации пользователя")
public record RegisterRequest(
        @Schema(description = "Имя Пользователя", example = "Иванов Иван Иванович")
        @NotBlank(message = "Поле имя пустое")
        String name,

        @Schema(description = "Email пользователя", example = "ivanIvanovich120@gmail.com")
        @NotBlank(message = "Поле email пустое")
        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "Неправильный формат email"
        )
        String email,

        @Schema(description = "Пароль пользователя")
        @NotBlank(message = "Поле пароль пустое")
        @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
        String password
) {
}
