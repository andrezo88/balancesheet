package com.andre.balancesheet.dtos;

import com.andre.balancesheet.models.TypeEnum;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BalanceDtoResponse {
    private String id;
    private Double amount;
    private String description;
    private TypeEnum type;
    private LocalDate date;
}
