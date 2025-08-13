package com.example.bankcards.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Сущность создания новой транзакции")
public record TransactionResponse(
        @Schema(description = "Id карты с которой происходит перевод")
        @NotNull
        Long fromCardId,

        @Schema(description = "Id карты на которую происходит перевод")
        @NotNull
        Long toCardId,

        @Schema(description = "Сумма перевода")
        @NotNull
        double sumTransaction
) {
}
