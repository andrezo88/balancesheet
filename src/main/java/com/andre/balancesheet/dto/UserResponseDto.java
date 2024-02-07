package com.andre.balancesheet.dto;

import com.andre.balancesheet.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class UserResponseDto {

    private String firstname;
    private String lastname;
    private String email;
    private Role role;
}
