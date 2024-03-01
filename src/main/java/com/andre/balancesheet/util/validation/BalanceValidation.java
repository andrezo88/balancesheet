package com.andre.balancesheet.util.validation;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.exceptions.service.BadRequestException;

import java.time.LocalDate;
import java.util.Objects;
public class BalanceValidation {

    public static void amountVerifierNull(BalanceDto dto) {
        if (dto.amount() == null)
            throw new BadRequestException("Amount cannot be null or empty");
    }

    public static void amountVerifierNegative(BalanceDto dto) {
        if (dto.amount() < 0)
            throw new BadRequestException("Amount must be greater than zero");
    }

    public static void descriptionVerifier(BalanceDto dto) {
        if (dto.description().isEmpty() || dto.description().length() > 50) {
            throw new BadRequestException("Description must be less than 50 characters");
        }
    }

    public static void dateVerifier(BalanceDto dto) {
        if (dto.date() != null && dto.date().isAfter(LocalDate.now()))
            throw new BadRequestException("Date cannot be greater than the current year");
    }

    public static BalanceDto isLateEntry(BalanceDto dto) {
        if (Objects.isNull(dto.date()))
            return dto.toBuilder()
                    .date(LocalDate.now())
                    .build();

        return dto;
    }
}
