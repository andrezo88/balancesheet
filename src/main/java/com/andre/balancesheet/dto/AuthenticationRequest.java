package com.andre.balancesheet.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationRequest {

    private String email;
    private String password;
}
