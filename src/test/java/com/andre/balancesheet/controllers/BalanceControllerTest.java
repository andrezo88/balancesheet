package com.andre.balancesheet.controllers;

import com.andre.balancesheet.config.auth.JwtService;
import com.andre.balancesheet.controller.BalanceController;
import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.fixtures.BalanceFixture;
import com.andre.balancesheet.service.AuthenticationService;
import com.andre.balancesheet.service.BalanceService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(controllers = BalanceController.class)
@AutoConfigureMockMvc(addFilters = false)
class BalanceControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    JwtService jwtService;

    @MockBean
    BalanceService balanceService;

    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user_test", authorities = "USER", roles = "USER")
    void shouldAllowAccessWhenAuthenticated() throws Exception {
        mockMvc.perform(get(BalanceFixture.URL_BALANCE))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user_test", authorities = "USER", roles = "USER")
    void shouldReturn201WhenInsertBalanceIsSucceded() throws Exception{
        BalanceDtoResponse balanceInserted = BalanceFixture.balanceDtoResponse;
        BalanceDto balanceDto = BalanceFixture.balanceDefaultDto;
        var json = new Gson().toJson(balanceDto);
        when(balanceService.save(balanceDto)).thenReturn(balanceInserted);

        mockMvc.perform(post(BalanceFixture.URL_BALANCE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/balance/1"))
                .andDo(print());
                verify(balanceService).save(balanceDto);
    }

    @Test
    void shouldReturn200WhenGetBalancesByIdIsSucceded() throws Exception {
        BalanceDtoResponse balanceDtoResponse = BalanceFixture.balanceDtoResponse;
        when(balanceService.getBalanceById("2")).thenReturn(balanceDtoResponse);

        mockMvc.perform(get(BalanceFixture.URL_BALANCE+"/{balanceId}", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.description").value("lunch"))
                .andExpect(jsonPath("$.type").value("DEBIT"))
                .andExpect(jsonPath("$.date").value("2021-10-10"));
        verify(balanceService).getBalanceById("2");
    }

    @Test
    @WithMockUser(username = "user_test", authorities = "USER", roles = "USER")
    void shouldReturn200WhenGetAllBalancesIsSucceded() throws Exception{
        var pageable= BalanceFixture.geraPageRequest(0,10, Sort.Direction.ASC);
        var listBalanceDtoResponse = BalanceFixture.geraPageBalanceDto();
        when(balanceService.getBalancePaged(pageable)).thenReturn(listBalanceDtoResponse);

        mockMvc.perform(get(BalanceFixture.URL_BALANCE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"));
        verify(balanceService).getBalancePaged(pageable);
    }

    @Test
    @WithMockUser(username = "user_test", authorities = "USER", roles = "USER")
    void shouldReturn200WhenGetBalanceByMonthRangeIsSucceded() throws Exception{
        var pageable= BalanceFixture.geraPageRequest(0,10, Sort.Direction.ASC);
        var listBalanceDtoResponse = BalanceFixture.geraPageBalanceDto();
        when(balanceService.getBalanceByMonthRange(pageable, "2021-01-01", "2021-10-30")).thenReturn(listBalanceDtoResponse);


        mockMvc.perform(get(BalanceFixture.URL_BALANCE+"/filter")
                .param("startDate", "2021-01-01")
                .param("endDate", "2021-10-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].amount").value(100.0))
                .andExpect(jsonPath("$.content[0].description").value("lunch"))
                .andExpect(jsonPath("$.content[0].type").value("CREDIT"))
                .andExpect(jsonPath("$.content[0].date").value("2021-10-10"));
        verify(balanceService).getBalanceByMonthRange(pageable, "2021-01-01", "2021-10-30");}

    @Test
    @WithMockUser(username = "user_test", authorities = "USER", roles = "USER")
    void shouldReturn200WhenUpdateIsSucceded() throws Exception{
        BalanceDto balanceDto = BalanceFixture.balanceDtoUpdate;
        BalanceDtoResponse balanceDtoResponseUpdated = BalanceFixture.balanceDtoResponseUpdate;
        when(balanceService.updateBalance("1", balanceDto)).thenReturn(balanceDtoResponseUpdated);

        mockMvc.perform(patch(BalanceFixture.URL_BALANCE+"/{balanceId}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(balanceDto)))
                .andExpect(status().isOk());
    }
}
