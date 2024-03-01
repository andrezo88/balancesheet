package com.andre.balancesheet.service;

import com.andre.balancesheet.config.auth.JwtService;
import com.andre.balancesheet.exceptions.service.BadRequestException;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.fixtures.UserFixture;
import com.andre.balancesheet.model.Token;
import com.andre.balancesheet.model.TokenType;
import com.andre.balancesheet.repository.TokenRepository;
import com.andre.balancesheet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    TokenRepository tokenRepository;

    @InjectMocks
    AuthenticationService authenticationService;

    @Mock
    JwtService jwtService;

    @Test
    void shouldRegisterUserAndReturnToken(){
        var userEntity = UserFixture.userDefaultUserRole;
        var dto = UserFixture.userDefaultDtoUserRole;
        var token = UserFixture.responseToken;

        when(userRepository.save(any())).thenReturn(userEntity);
        when(passwordEncoder.encode(any())).thenReturn(dto.password());
        when(jwtService.generateToken(userEntity)).thenReturn(String.valueOf(token));
        var result = authenticationService.register(dto);

        assertThat(result).toString().equals(token.token().toString());
        verify(userRepository).save(userEntity);
        verify(passwordEncoder).encode(any());
        verify(jwtService).generateToken(userEntity);
    }

    @Test
    void shouldReturnBadRequestExceptionWhenUserAlreadyExists() {
        var email = "email@email.com";
        var userEntity = UserFixture.userDefaultEntityUserRole;
        var userDto = UserFixture.userDefaultDtoUserRole;

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        assertThatThrownBy(() -> authenticationService.register(userDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email %s is already registered", email);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldReturnTokenWhenUserIsAuthenticateWithSuccess() {
        var email = "email@email.com";
        var userEntity = UserFixture.userDefaultUserRole;
        var dto = UserFixture.authenticationRequest;
        var token = UserFixture.responseToken;

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(userEntity));
        when(jwtService.generateToken(userEntity)).thenReturn(String.valueOf(token));
        var result = authenticationService.authenticate(dto);


        assertThat(result).isNotNull();
        verify(userRepository).findByEmail(email);
        verify(jwtService).generateToken(userEntity);
    }

    @Test
    void shouldReturnIdNotFoundExceptionWhenUserAlreadyExists() {
        var email = "email@email.com";
        var authenticationRequest = UserFixture.authenticationRequest;

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationService.authenticate(authenticationRequest))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessageContaining("Email %s not found: ", email);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void shoudlGetUserLogged() {
        var email = "email@email.com";
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.of(UserFixture.userDefaultEntityUserRole));
        SecurityContextHolder.setContext(securityContext);

        var result = authenticationService.getUser();
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    void shouldRevokeAllUserTokens() {
        List<Token> userTokenList=new ArrayList<Token>();
        Token token = new Token("1", "token", TokenType.BEARER, false, false, "1");

        userTokenList.add(token);

        when(tokenRepository.findAllValidTokensByUser(any())).thenReturn(userTokenList);

        authenticationService.revokeAllUserTokens(UserFixture.userDefaultEntityUserRole);

        verify(tokenRepository).findAllValidTokensByUser(any());
        verify(tokenRepository).saveAll(any());

        for (Token token1 : userTokenList) {
            assertThat(token1.isExpired()).isTrue();
            assertThat(token1.isRevoked()).isTrue();
        }


    }

}
