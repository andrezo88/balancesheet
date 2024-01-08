package com.andre.balancesheet.models;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "balance")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BalanceModel {
    @Id
    private String id;
    private Double amount;
    private String description;
    private TypeEnum type;
    @NotNull
    private Boolean isLateEntry;
    private LocalDate date;
    private LocalDateTime createdAt;


}
