package com.andre.balancesheet.dto;

import com.andre.balancesheet.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@Data
public class RegisterRequest {

    @NotNull
    @NotBlank
    private String firstname;
    @NotNull
    @NotBlank
    private String lastname;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String password;
    private Role role;
}
