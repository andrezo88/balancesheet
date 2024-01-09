package com.andre.balancesheet.utils.mappers;

import com.andre.balancesheet.dtos.BalanceDtoResponse;
import com.andre.balancesheet.fixtures.BalanceFixture;
import com.andre.balancesheet.models.BalanceModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BalanceMapperTest {

    @Test
    void shouldReturnBalanceDtoResponse() {

        var balanceModel = BalanceFixture.balanceDefault;

        BalanceDtoResponse response = BalanceFixture.INSTANCE_MAPPER.convertBalanceToBalanceDto(balanceModel);

        assertEquals(balanceModel.getId(), response.getId());
        assertEquals(balanceModel.getAmount(), response.getAmount());
        assertEquals(balanceModel.getDescription(), response.getDescription());
    }

    @Test
    void shouldReturnBalanceModel() {

        var balanceDto = BalanceFixture.balanceDefaultDto;

        BalanceModel response = BalanceFixture.INSTANCE_MAPPER.convertBalanceDtoToBalance(balanceDto);

        assertEquals(balanceDto.getId(), response.getId());
        assertEquals(balanceDto.getAmount(), response.getAmount());
        assertEquals(balanceDto.getDescription(), response.getDescription());
    }

    @Test
    void shouldReturnListBalanceDtoResponse() {

        var listBalanceModel = BalanceFixture.listBalanceModel();

        List<BalanceDtoResponse> response = BalanceFixture.INSTANCE_MAPPER.convertListBalanceToListBalanceDtoResponse(listBalanceModel);

        assertEquals(listBalanceModel.get(0).getId(), response.get(0).getId());
    }

    @Test
    void shouldReturnNullWhenBalanceModelIsNull() {

        BalanceDtoResponse response = BalanceFixture.INSTANCE_MAPPER.convertBalanceToBalanceDto(null);

        assertEquals(null, response);
    }

    @Test
    void shouldReturnNullWhenBalanceDtoIsNull() {

        BalanceModel response = BalanceFixture.INSTANCE_MAPPER.convertBalanceDtoToBalance(null);

        assertEquals(null, response);
    }

    @Test
    void shouldReturnNullWhenListBalanceModelIsNull() {

        List<BalanceDtoResponse> response = BalanceFixture.INSTANCE_MAPPER.convertListBalanceToListBalanceDtoResponse(null);

        assertEquals(null, response);
    }
}
