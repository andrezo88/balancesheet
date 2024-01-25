package com.andre.balancesheet.fixtures;

import com.andre.balancesheet.dto.AuthenticationRequest;
import com.andre.balancesheet.dto.AuthenticationResponse;
import com.andre.balancesheet.dto.RegisterRequest;
import com.andre.balancesheet.model.Role;
import com.andre.balancesheet.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserFixture {


    private static String passwordEncoded = new BCryptPasswordEncoder().encode("senha");
    public static final User userDefault = User.builder()
            .firstname("Usuario")
            .lastname("Teste")
            .email("email@email.com")
            .password(passwordEncoded)
            .role(Role.USER)
            .build();

    public static final User userDefaultEntity = User.builder()
            .id("1")
            .firstname("Usuario")
            .lastname("Teste")
            .email("email@email.com")
            .password(passwordEncoded)
            .role(Role.USER)
            .build();

    public static final RegisterRequest userDefaultDto = RegisterRequest.builder()
            .firstname("Usuario")
            .lastname("Teste")
            .email("email@email.com")
            .password(passwordEncoded)
            .build();

    public static final AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
            .email("email@email.com")
            .password(passwordEncoded)
            .build();

    public static final AuthenticationResponse responseToken = AuthenticationResponse.builder()
            .token("token")
            .build();

    public static final Authentication userLogged = SecurityContextHolder.getContext().getAuthentication();

}
