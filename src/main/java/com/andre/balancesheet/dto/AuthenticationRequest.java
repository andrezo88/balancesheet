package com.andre.balancesheet.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AuthenticationRequest {

    private String email;
    private String password;
}
