package com.andre.balancesheet.dtos;

import com.andre.balancesheet.configs.LocalDateTypeAdapter;
import com.andre.balancesheet.models.TypeEnum;
import com.google.gson.annotations.JsonAdapter;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BalanceDto {
    private Double amount;
    private String description;
    private TypeEnum type;
    @JsonAdapter(LocalDateTypeAdapter.class)
    private LocalDate date = LocalDate.now();
}
