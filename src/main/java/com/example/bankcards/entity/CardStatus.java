package com.example.bankcards.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус карты")
public enum CardStatus {
    ACTIVE, BLOCKED, EXPIRED;
}
