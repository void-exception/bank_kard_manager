package com.example.bankcards.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Информация о транзакции")
public record TransactionDTO(
        @Schema(description = "Id транзакции")
        Long id,

        @Schema(description = "Email пользователя")
        String email_user,

        @Schema(description = "Id карты с которой происходит перевод")
        Long id_from_card,

        @Schema(description = "Id карты на которую происходит перевод")
        Long id_to_card,

        @Schema(description = "Дата перевода", example = "2025-12-03T10:15:30")
        LocalDateTime localDateTime,

        @Schema(description = "Сумма перевода")
        double sum_transaction
) {
}
