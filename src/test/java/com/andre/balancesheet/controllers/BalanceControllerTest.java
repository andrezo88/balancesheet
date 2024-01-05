package com.andre.balancesheet.controllers;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.dtos.BalanceDtoResponse;
import com.andre.balancesheet.fixtures.BalanceFixture;
import com.andre.balancesheet.models.BalanceModel;
import com.andre.balancesheet.services.BalanceService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = BalanceController.class)
class BalanceControllerTest {

    @MockBean
    BalanceService balanceService;

    @Autowired
    MockMvc mockMvc;

    private static final String URL = "http://localhost:8080/";

    @Test
    void shouldReturn201WhenInsertBalanceIsSucceded() throws Exception{
        BalanceModel balanceInserted = BalanceFixture.balanceDefault;
        BalanceDto balanceDto = BalanceFixture.balanceDefaultDto;
        var json = new Gson().toJson(balanceDto);
        when(balanceService.save(balanceDto)).thenReturn(balanceInserted);

        mockMvc.perform(post(URL+BalanceFixture.URL_BALANCE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost:8080/v1/balance/1"));
                verify(balanceService).save(balanceDto);
    }

    @Test
    void shouldReturn201WhenInsertedBalanceIslateTrueIsSucceded() throws Exception {
        BalanceModel balanceInserted = BalanceFixture.balanceLateEntry;
        BalanceDto balanceDto = BalanceFixture.balanceLateEntryDto;
        var json = new Gson().toJson(balanceDto);
        when(balanceService.save(balanceDto)).thenReturn(balanceInserted);

        mockMvc.perform(post(URL+BalanceFixture.URL_BALANCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost:8080/v1/balance/2"));
        verify(balanceService).save(balanceDto);
    }

    @Test
    void shouldReturn200WhenGetBalancesByIdIsSucceded() throws Exception {
        BalanceDtoResponse balanceDtoResponse = BalanceFixture.balanceDtoResponse;
        when(balanceService.getBalanceById("2")).thenReturn(balanceDtoResponse);

        mockMvc.perform(get(URL+BalanceFixture.URL_BALANCE+"/{balanceId}", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.description").value("lunch"))
                .andExpect(jsonPath("$.type").value("DEBIT"))
                .andExpect(jsonPath("$.date").value("2021-10-10"));
    }

    @Test
    void shouldReturn200WhenGetAllBalancesIsSucceded() throws Exception{
        List<BalanceDtoResponse> listBalanceDtoResponse = BalanceFixture.listBalanceDtoResponse();
        when(balanceService.getBalance()).thenReturn(listBalanceDtoResponse);

        mockMvc.perform(get(URL+BalanceFixture.URL_BALANCE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[0].description").value("lunch"))
                .andExpect(jsonPath("$[0].type").value("DEBIT"))
                .andExpect(jsonPath("$[0].date").value("2021-10-10"));
    }

    @Test
    void shouldReturn200WhenUpdateIsSucceded() throws Exception{
        BalanceDto balanceDto = BalanceFixture.balanceDtoUpdate;
        BalanceDtoResponse balanceDtoResponseUpdated = BalanceFixture.balanceDtoResponseUpdate;
        when(balanceService.updateBalance("1", balanceDto)).thenReturn(balanceDtoResponseUpdated);

        mockMvc.perform(patch(URL+BalanceFixture.URL_BALANCE+"/{balanceId}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(balanceDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.amount").value(150.0))
                .andExpect(jsonPath("$.description").value("lunch"))
                .andExpect(jsonPath("$.type").value("CASH"))
                .andExpect(jsonPath("$.date").value("2024-01-02"));
    }
}
