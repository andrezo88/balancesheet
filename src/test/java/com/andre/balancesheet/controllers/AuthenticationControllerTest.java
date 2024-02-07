package com.andre.balancesheet.controllers;

import com.andre.balancesheet.config.auth.JwtService;
import com.andre.balancesheet.controller.AuthenticationController;
import com.andre.balancesheet.fixtures.UserFixture;
import com.andre.balancesheet.repository.TokenRepository;
import com.andre.balancesheet.service.AuthenticationService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    JwtService jwtService;

    @MockBean
    TokenRepository TokenRepository;

    @Autowired
    public MockMvc mockMvc;

    @Test
    void shouldReturn201WhenRegisterUserIsSuccess() throws Exception {
        var userDto = UserFixture.userDefaultDtoUserRole;
        var response = UserFixture.responseToken;
        var json = new Gson().toJson(userDto);
        when(authenticationService.register(userDto)).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().equals(response);
        verify(authenticationService).register(userDto);
    }

    @Test
    void shouldReturn200WhenAuthenticateIsSuccess() throws Exception {
        var requestAuthentication = UserFixture.authenticationRequest;
        var response = UserFixture.responseToken;
        var json = new Gson().toJson(requestAuthentication);

        when(authenticationService.authenticate(requestAuthentication)).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().equals(response);
        verify(authenticationService).authenticate(requestAuthentication);
    }
}
