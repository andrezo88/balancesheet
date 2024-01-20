package com.andre.balancesheet.dto;

import com.andre.balancesheet.config.adapter.LocalDateTimeTypeAdapter;
import com.andre.balancesheet.config.adapter.LocalDateTypeAdapter;
import com.andre.balancesheet.model.TypeEnum;
import com.google.gson.annotations.JsonAdapter;
import jakarta.validation.constraints.NotBlank;
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
    private Double amount;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private TypeEnum type;
    @JsonAdapter(LocalDateTypeAdapter.class)
    private LocalDate date = LocalDate.now();
    @JsonAdapter(LocalDateTimeTypeAdapter.class)
    private LocalDateTime createdAt = LocalDateTime.now();
}
