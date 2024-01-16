package com.andre.balancesheet.dto;

import com.andre.balancesheet.config.adapter.LocalDateTypeAdapter;
import com.andre.balancesheet.model.TypeEnum;
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
