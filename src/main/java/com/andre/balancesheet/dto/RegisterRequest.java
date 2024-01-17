package com.andre.balancesheet.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
