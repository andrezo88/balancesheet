package com.andre.balancesheet.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "balance")
@Builder
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BalanceModel {
    @Id
    private String id;
    private Double amount;
    private String description;
    private TypeEnum type;
    private LocalDate date;
    private LocalDateTime createdAt;
    private String userId;
}
