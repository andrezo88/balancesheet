package com.andre.balancesheet.dtos;

import com.andre.balancesheet.configs.LocalDateTimeTypeAdapter;
import com.andre.balancesheet.configs.LocalDateTypeAdapter;
import com.andre.balancesheet.models.TypeEnum;
import com.google.gson.annotations.JsonAdapter;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BalanceDto {
    private String id;
    private Double amount;
    private String description;
    private TypeEnum type;
    @NotNull
    private Boolean isLateEntry = false;
    @JsonAdapter(LocalDateTypeAdapter.class)
    private LocalDate date = LocalDate.now();
    @JsonAdapter(LocalDateTimeTypeAdapter.class)
    private LocalDateTime createdAt = LocalDateTime.now();
}
