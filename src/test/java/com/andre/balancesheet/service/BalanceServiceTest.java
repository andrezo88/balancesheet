package com.andre.balancesheet.service;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.fixtures.BalanceFixture;
import com.andre.balancesheet.models.BalanceModel;
import com.andre.balancesheet.models.TypeEnum;
import com.andre.balancesheet.repositories.BalanceRepository;
import com.andre.balancesheet.services.BalanceService;
import com.andre.balancesheet.utils.mappers.BalanceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Spy
    private final BalanceMapper balanceMapper = Mappers.getMapper(BalanceMapper.class);

    private final BalanceRepository balanceRepository = Mockito.mock(BalanceRepository.class);

    private final BalanceService balanceService = new BalanceService(balanceMapper, balanceRepository);


    @Test
    void shouldInsertBalance() {
        BalanceDto balanceDto = BalanceDto.builder()
                .amount(1000.00)
                .description("Salary")
                .type(TypeEnum.CREDIT)
                .isLateEntry(false)
                .date(LocalDate.now())
                .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
                .build();

        BalanceModel balanceModel = BalanceModel.builder()
                .amount(1000.00)
                .description("Salary")
                .type(TypeEnum.CREDIT)
                .isLateEntry(false)
                .date(LocalDate.now())
                .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
                .build();

        when(balanceRepository.save(balanceModel)).thenReturn(balanceModel);

        var result = balanceService.save(balanceDto);

        assertThat(result).isEqualTo(balanceModel);

        verify(balanceRepository).save(balanceModel);
    }

    @Test
    void shouldInsertBalanceLateEntry() {
        BalanceDto balanceDto = BalanceDto.builder()
                .amount(1000.00)
                .description("Salary")
                .type(TypeEnum.CREDIT)
                .isLateEntry(true)
                .date(LocalDate.parse("2024-01-01"))
                .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
                .build();

        var balanceModel = balanceMapper.convertBalanceDtoToBalance(balanceDto);

        when(balanceRepository.save(balanceModel)).thenReturn(balanceModel);

        var result = balanceService.save(balanceDto);

        assertThat(result).isEqualTo(balanceModel);

        verify(balanceRepository).save(balanceModel);
    }

    @Test
    void shouldGetBalanceById() {

        BalanceModel balanceModel = BalanceModel.builder()
                .id("1")
                .amount(1000.00)
                .description("Salary")
                .type(TypeEnum.CREDIT)
                .isLateEntry(false)
                .date(LocalDate.now())
                .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
                .build();

        var balanceDto = balanceMapper.convertBalanceToBalanceDto(balanceModel);

        when(balanceRepository.findById("1")).thenReturn(java.util.Optional.of(balanceModel));

        var result = balanceService.getBalanceById("1");

        assertThat(result).isEqualTo(balanceDto);

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
        BalanceDto balanceDto = BalanceDto.builder()
                .id("1")
                .amount(1100.00)
                .description("Salary")
                .type(TypeEnum.CREDIT)
                .isLateEntry(false)
                .date(LocalDate.parse("2024-01-01"))
                .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
                .build();

        BalanceModel balanceModel = BalanceModel.builder()
                .id("1")
                .amount(1100.00)
                .description("Salary")
                .type(TypeEnum.CREDIT)
                .isLateEntry(false)
                .date(LocalDate.parse("2024-01-01"))
                .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
                .build();

        var balanceDtoResponse = balanceMapper.convertBalanceToBalanceDto(balanceModel);

        when(balanceRepository.findById("1")).thenReturn(java.util.Optional.of(balanceModel));
        when(balanceRepository.save(Mockito.any(BalanceModel.class))).thenReturn(balanceModel);

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
