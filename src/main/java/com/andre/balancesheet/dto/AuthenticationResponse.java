package com.andre.balancesheet.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@Data
public class AuthenticationResponse {
    private String token;
}
