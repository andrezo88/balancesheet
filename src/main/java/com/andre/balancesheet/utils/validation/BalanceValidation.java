package com.andre.balancesheet.utils.validation;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.exceptions.service.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

import static java.util.Optional.empty;
public class BalanceValidation {

    public static void amountVerifier(BalanceDto dto) {
        if (dto.getAmount() == null || dto.getAmount().equals(empty()))
            throw new BadRequestException("Amount cannot be null or empty");
        else if (dto.getAmount() <= 0) {
            throw new BadRequestException("Amount must be greater than zero");
        }
    }

    public static void descriptionVerifier(BalanceDto dto) {
        if (dto.getDescription().length() < 1 || dto.getDescription().length() > 50) {
            throw new BadRequestException("Description must be less than 50 characters");
        }
    }

    public static void dateVerifier(BalanceDto dto) {
        if (dto.getDate().isAfter(LocalDate.now()))
            throw new BadRequestException("Date cannot be greater than the current year");
    }

    public static BalanceDto isLateEntry( BalanceDto dto) {
        if (Objects.isNull(dto.getDate()))
            dto.setDate(LocalDate.now());
        return dto;
    }
}
