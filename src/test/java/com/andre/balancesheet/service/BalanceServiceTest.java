package com.andre.balancesheet.service;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.fixtures.BalanceFixture;
import com.andre.balancesheet.models.BalanceModel;
import com.andre.balancesheet.repositories.BalanceRepository;
import com.andre.balancesheet.services.BalanceService;
import com.andre.balancesheet.utils.mappers.BalanceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Spy
    private final BalanceMapper balanceMapper = Mappers.getMapper(BalanceMapper.class);

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceService balanceService;


    @Test
    void shouldInsertBalance() {
        BalanceDto balanceDto = BalanceFixture.balanceDefaultDto;

        var balanceEntity = balanceMapper.convertBalanceDtoToBalance(balanceDto);

        when(balanceRepository.save(balanceEntity)).thenReturn(balanceEntity);

        var result = balanceService.save(balanceDto);

        assertThat(result.getAmount()).isEqualTo(balanceEntity.getAmount());

        verify(balanceRepository).save(balanceEntity);
    }

    @Test
    void shouldInsertBalanceLateEntry() {
        BalanceDto balanceDto = BalanceFixture.balanceLateEntryDto;

        var balanceModel = balanceMapper.convertBalanceDtoToBalance(balanceDto);

        when(balanceRepository.save(balanceModel)).thenReturn(balanceModel);

        var result = balanceService.save(balanceDto);

        assertThat(result.getAmount()).isEqualTo(balanceModel.getAmount());

        verify(balanceRepository).save(balanceModel);
    }

    @Test
    void shouldGetBalanceById() {

        BalanceModel balanceModel = BalanceFixture.balanceDefault;

        var balanceDto = balanceMapper.convertBalanceToBalanceDto(balanceModel);

        when(balanceRepository.findById("1")).thenReturn(java.util.Optional.of(balanceModel));

        var result = balanceService.getBalanceById("1");

        assertThat(result.getAmount()).isEqualTo(balanceDto.getAmount());

        verify(balanceRepository).findById("1");
    }

    @Test
    void shouldGetAllBalancesList() {
        List<BalanceModel> listBalanceModel = BalanceFixture.listBalanceModel();
        var listBalanceDto = balanceMapper.convertListBalanceToListBalanceDtoResponse(listBalanceModel);

        when(balanceRepository.findAll()).thenReturn(listBalanceModel);

        var result = balanceService.getBalance();

        assertEquals(result, listBalanceDto);

        verify(balanceRepository).findAll();
    }

    @Test
    void shouldUpdateBalance() {
        BalanceDto balanceDto = BalanceFixture.balanceDtoUpdate;

        BalanceModel balanceModel = BalanceFixture.balanceUpdated;

        var balanceDtoResponse = balanceMapper.convertBalanceToBalanceDto(balanceModel);

        when(balanceRepository.findById("1")).thenReturn(java.util.Optional.of(balanceModel));
        when(balanceRepository.save(balanceModel)).thenReturn(balanceModel);

        var result = balanceService.updateBalance("1", balanceDto);

        assertThat(result).isEqualTo(balanceDtoResponse);

        verify(balanceRepository).save(balanceModel);
    }

    @Test
    void shouldThrowExceptionWhenIdNotExists() {
        String id = "1";

        when(balanceRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> balanceService.getBalanceById(id))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessageContaining("Id 1 not found: ");

        verify(balanceRepository).findById(id);
    }

}
