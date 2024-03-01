package com.andre.balancesheet.dto;

import com.andre.balancesheet.model.Role;
import lombok.Builder;

@Builder
public record UserResponseDto(
        String firstname,
        String lastname,
        String email,
        Role role
) {}
