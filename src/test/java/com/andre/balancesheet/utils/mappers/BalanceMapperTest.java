package com.andre.balancesheet.utils.mappers;

import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.fixtures.BalanceFixture;
import com.andre.balancesheet.model.BalanceModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BalanceMapperTest {

    @Test
    void shouldReturnBalanceDtoResponse() {

        var balanceModel = BalanceFixture.balanceDefault;

        BalanceDtoResponse response = BalanceFixture.INSTANCE_MAPPER.convertBalanceToBalanceDto(balanceModel);

        assertEquals(balanceModel.getId(), response.id());
        assertEquals(balanceModel.getAmount(), response.amount());
        assertEquals(balanceModel.getDescription(), response.description());
    }

    @Test
    void shouldReturnBalanceModel() {

        var balanceDto = BalanceFixture.balanceDefaultDto;

        BalanceModel response = BalanceFixture.INSTANCE_MAPPER.convertBalanceDtoToBalance(balanceDto);

        assertEquals(balanceDto.amount(), response.getAmount());
        assertEquals(balanceDto.description(), response.getDescription());
    }

    @Test
    void shouldReturnNullWhenBalanceModelIsNull() {

        BalanceDtoResponse response = BalanceFixture.INSTANCE_MAPPER.convertBalanceToBalanceDto(null);

        assertNull(response);
    }

    @Test
    void shouldReturnNullWhenBalanceDtoIsNull() {

        BalanceModel response = BalanceFixture.INSTANCE_MAPPER.convertBalanceDtoToBalance(null);

        assertNull(response);
    }

}
