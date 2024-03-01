package com.andre.balancesheet.dto;

import com.andre.balancesheet.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record UpdateUser(
        @NotNull
        @NotBlank
        String firstname,
        @NotNull
        @NotBlank
        String lastname,
        @NotNull
        @NotBlank
        @Email
        String email,
        @NotNull
        @NotBlank
        String password,
        Role role
) {}
