package com.andre.balancesheet.fixtures;

import com.andre.balancesheet.dto.AuthenticationRequest;
import com.andre.balancesheet.dto.AuthenticationResponse;
import com.andre.balancesheet.dto.RegisterRequest;
import com.andre.balancesheet.model.Role;
import com.andre.balancesheet.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserFixture {


    private static final String passwordEncoded = new BCryptPasswordEncoder().encode("senha");
    public static final User userDefaultUserRole = User.builder()
            .firstname("Usuario")
            .lastname("Teste")
            .email("email@email.com")
            .password(passwordEncoded)
            .role(Role.USER)
            .build();

    public static final User userDefaultUserAdmin = User.builder()
            .firstname("Usuario")
            .lastname("Teste")
            .email("admin@email.com")
            .password(passwordEncoded)
            .role(Role.ADMIN)
            .build();

    public static final User userDefaultEntityUserRole = User.builder()
            .id("1")
            .firstname("Usuario")
            .lastname("Teste")
            .email("email@email.com")
            .password(passwordEncoded)
            .role(Role.USER)
            .build();

    public static final RegisterRequest userDefaultDtoUserRole = RegisterRequest.builder()
            .firstname("Usuario")
            .lastname("Teste")
            .email("email@email.com")
            .password(passwordEncoded)
            .role(Role.USER)
            .build();

    public static final RegisterRequest userDefaultDtoUserAdmin = RegisterRequest.builder()
            .firstname("Usuario")
            .lastname("Teste")
            .email("admin@email.com")
            .password(passwordEncoded)
            .role(Role.ADMIN)
            .build();

    public static final AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
            .email("email@email.com")
            .password(passwordEncoded)
            .build();

    public static final AuthenticationResponse responseToken = AuthenticationResponse.builder()
            .token("token")
            .build();

}
