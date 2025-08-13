package com.example.bankcards.dto.card;

import com.example.bankcards.entity.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.YearMonth;

@Schema(description = "Информация о карте")
public record CardDTO(
        @Schema(description = "Id карты")
        Long id,

        @Schema(description = "Номер карты", example = "4400223355887733")
        String number,

        @Schema(description = "Имя пользователя, которому пренадлежит карта")
        String userName,

        @Schema(description = "Email пользователя, которому пренадлежит карта")
        String userEmail,

        @Schema(description = "Дата окончания действия карты", example = "2027-03")
        YearMonth endDate,

        @Schema(description = "Статус карты")
        CardStatus status,

        @Schema(description = "Баланс карты")
        double balance
) {
}
