package com.andre.balancesheet.util.validation;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.exceptions.service.BadRequestException;

import java.time.LocalDate;
import java.util.Objects;
public class BalanceValidation {

    public static void amountVerifierNull(BalanceDto dto) {
        if (dto.getAmount() == null)
            throw new BadRequestException("Amount cannot be null or empty");
    }

    public static void amountVerifierNegative(BalanceDto dto) {
        if (dto.getAmount() < 0)
            throw new BadRequestException("Amount cannot be less than 0");
    }

    public static void descriptionVerifier(BalanceDto dto) {
        if (dto.getDescription().isEmpty() || dto.getDescription().length() > 50) {
            throw new BadRequestException("Description must be less than 50 characters");
        }
    }

    public static void dateVerifier(BalanceDto dto) {
        if (dto.getDate().isAfter(LocalDate.now()))
            throw new BadRequestException("Date cannot be greater than the current year");
    }

    public static void isLateEntry(BalanceDto dto) {
        if (Objects.isNull(dto.getDate()))
            dto.setDate(LocalDate.now());
    }
}
