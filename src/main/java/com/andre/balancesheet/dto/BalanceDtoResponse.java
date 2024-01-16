package com.andre.balancesheet.dto;

import com.andre.balancesheet.model.TypeEnum;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode

public class BalanceDtoResponse {
    private String id;
    private Double amount;
    private String description;
    private TypeEnum type;
    private LocalDate date;
}
