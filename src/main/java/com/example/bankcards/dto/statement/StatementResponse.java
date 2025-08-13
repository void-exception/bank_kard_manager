package com.example.bankcards.dto.statement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Сущность ответа на заявку")
public record StatementResponse(
        @Schema(description = "Id заявки")
        @NotNull
        Long idStatement,

        @Schema(description = "Решение по заявки", allowableValues = {"approved", "rejection"})
        @NotNull
        String newStatus
) {
}
