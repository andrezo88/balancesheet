package com.andre.balancesheet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Document(collection = "token")
@Data
@Builder
@AllArgsConstructor
public class Token {

    @Id
    private String id;
    private String authToken;
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    private String userId;
}
