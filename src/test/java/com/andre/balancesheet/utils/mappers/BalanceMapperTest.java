package com.andre.balancesheet.utils.mappers;

import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.fixtures.BalanceFixture;
import com.andre.balancesheet.model.BalanceModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        assertEquals(balanceDto.getAmount(), response.getAmount());
        assertEquals(balanceDto.getDescription(), response.getDescription());
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

    @Test
    void shouldReturnNullWhenListBalanceModelIsNull() {

        List<BalanceDtoResponse> response = BalanceFixture.INSTANCE_MAPPER.convertListBalanceToListBalanceDtoResponse(null);

        assertNull(response);
    }
}
