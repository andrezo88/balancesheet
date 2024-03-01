package com.andre.balancesheet.dto;

import com.andre.balancesheet.model.TypeEnum;
import lombok.Builder;

import java.time.LocalDate;

@Builder


public record BalanceDtoResponse(
        String id,
        Double amount,
        String description,
        TypeEnum type,
        LocalDate date
        ) {

}
