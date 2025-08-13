package com.example.bankcards.dto.statement;


import com.example.bankcards.entity.Statement;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Информация о заявке")
public record StatementDTO(
        @Schema(description = "Id завявки")
        Long id,

        @Schema(description = "Id карты")
        Long card_id,

        @Schema(description = "Email пользователя")
        String user_email,

        @Schema(description = "Дата создания заявки", example = "2025-12-03T10:15:30")
        LocalDateTime date_of_dispatch,

        @Schema(description = "Статус заявки")
        String status
) {
    public StatementDTO(Statement statement) {
        this(
                statement.getId(),
                statement.getCard().getId(),
                statement.getCard().getUser().getEmail(),
                statement.getDate_of_dispatch(),
                statement.getStatus()
        );
    }
}