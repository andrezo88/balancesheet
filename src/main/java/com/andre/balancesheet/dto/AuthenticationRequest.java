package com.andre.balancesheet.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@Data
public class AuthenticationRequest {

    private String email;
    private String password;
}
