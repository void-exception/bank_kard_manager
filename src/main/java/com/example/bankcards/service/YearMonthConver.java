package com.example.bankcards.service;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.YearMonth;

/**
 * Конвертер для сохранения YearMonth в базу как строку и чтения обратно.
 * Преобразует YearMonth в формат "YYYY-MM" и обратно.
 */
@Converter(autoApply = true)
public class YearMonthConver implements AttributeConverter<YearMonth, String> {
    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return dbData != null ? YearMonth.parse(dbData) : null;
    }
}
