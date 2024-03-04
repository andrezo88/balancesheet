package com.andre.balancesheet.controllers;

import com.andre.balancesheet.config.auth.JwtService;
import com.andre.balancesheet.controller.AdminController;
import com.andre.balancesheet.dto.RegisterRequest;
import com.andre.balancesheet.dto.UpdateUser;
import com.andre.balancesheet.dto.UserResponseDto;
import com.andre.balancesheet.fixtures.UserFixture;
import com.andre.balancesheet.repository.TokenRepository;
import com.andre.balancesheet.service.AdminService;
import com.andre.balancesheet.service.AuthenticationService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.andre.balancesheet.fixtures.UserFixture.URL_BALANCE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    JwtService jwtService;

    @MockBean
    AdminService adminService;

    @MockBean
    TokenRepository tokenRepository;

    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser
    void shoudlReturn200WhenUpdateHasUserAuthoredAndIsSucceeded() throws Exception {
        UserResponseDto response = UserFixture.userAdminResponseDto;
        UpdateUser dto = UserFixture.userDefaultDtoUserAdminUpdateUser;
        var json = new Gson().toJson(dto);

        when(adminService.updateUserRole("1", dto)).thenReturn(response);

        mockMvc.perform(patch(URL_BALANCE+"/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk()
                );
        verify(adminService).updateUserRole("1", dto);
    }

}
