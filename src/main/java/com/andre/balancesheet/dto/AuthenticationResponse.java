package com.andre.balancesheet.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AuthenticationResponse {
    private String token;
}
