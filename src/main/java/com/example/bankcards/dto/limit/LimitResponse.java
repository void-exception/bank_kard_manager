package com.example.bankcards.dto.limit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Сущность указания лимита")
public record LimitResponse(
        @Schema(description = "Id карты")
        @NotNull
        Long cardId,

        @Schema(description = "Сумма лимита")
        @NotNull
        double sum,

        @Schema(description = "Дата окончания действия лимита", example = "2025-12-03T10:15:30")
        @NotNull
        LocalDateTime endLimit
) {
}
