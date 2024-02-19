package com.andre.balancesheet.dto;

import com.andre.balancesheet.model.TypeEnum;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@EqualsAndHashCode

public class BalanceDtoResponse {
    private String id;
    private Double amount;
    private String description;
    private TypeEnum type;
    private LocalDate date;
}
