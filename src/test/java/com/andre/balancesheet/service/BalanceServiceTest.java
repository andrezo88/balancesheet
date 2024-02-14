package com.andre.balancesheet.service;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.exceptions.service.BadRequestException;
import com.andre.balancesheet.exceptions.service.ForbiddenException;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.fixtures.BalanceFixture;
import com.andre.balancesheet.fixtures.UserFixture;
import com.andre.balancesheet.model.BalanceModel;
import com.andre.balancesheet.model.TypeEnum;
import com.andre.balancesheet.model.User;
import com.andre.balancesheet.repository.BalanceRepository;
import com.andre.balancesheet.util.mapper.BalanceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Spy
    private final BalanceMapper balanceMapper = Mappers.getMapper(BalanceMapper.class);

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private AuthenticationService service;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    void shouldInsertBalance() {
        User userEntity = UserFixture.userDefaultEntityUserRole;
        BalanceDto balanceDto = BalanceFixture.balanceDefaultDto;
        BalanceModel balanceEntity = BalanceFixture.balanceDefault;
        BalanceDtoResponse balanceDtoResponse = balanceMapper.convertBalanceToBalanceDto(balanceEntity);


        when(service.getUser()).thenReturn(userEntity);
        when(balanceRepository.save(balanceEntity)).thenReturn(balanceEntity);
        when(balanceMapper.convertBalanceToBalanceDto(balanceEntity)).thenReturn(balanceDtoResponse);
        var result = balanceService.save(balanceDto);

        assertThat(result.getAmount()).isEqualTo(balanceEntity.getAmount());
        verify(balanceRepository).save(balanceEntity);
    }

    @Test
    void shouldInsertBalanceWhenDateisNull() {
        User userEntity = UserFixture.userDefaultEntityUserRole;
        BalanceDto balanceDto = new BalanceDto(100.00, "lunch", TypeEnum.DEBIT, null, LocalDateTime.parse("2024-01-01T10:00:00"));
        BalanceModel balanceEntity = BalanceFixture.balanceDefault;
        BalanceDtoResponse balanceDtoResponse = balanceMapper.convertBalanceToBalanceDto(balanceEntity);


        when(service.getUser()).thenReturn(userEntity);
        doAnswer(invocation -> {
            balanceDto.setDate(LocalDate.now());
            balanceService.save(balanceDto);
            return null;
        }).when(balanceRepository).save(balanceEntity);
        when(balanceRepository.save(balanceEntity)).thenReturn(balanceEntity);
        when(balanceMapper.convertBalanceToBalanceDto(balanceEntity)).thenReturn(balanceDtoResponse);
        var result = balanceService.save(balanceDto);

        assertThat(result.getAmount()).isEqualTo(balanceEntity.getAmount());
        verify(balanceRepository).save(balanceEntity);
    }


    @Test
    void shouldThrowBadRequestExceptionWhenInsertBalanceAmountIsNull() {
        BalanceDto balance = new BalanceDto(null, "Description", TypeEnum.CREDIT, null, null);

        assertThrows(BadRequestException.class, () -> {
            balanceService.save(balance);
        });

        verify(balanceRepository, never()).save(any(BalanceModel.class));

    }

    @Test
    void shouldThrowBadRequestExceptionWhenInsertBalanceAmountIsNegative() {
        BalanceDto balanceDto = new BalanceDto(-1.00, "Description", TypeEnum.CREDIT, null, null);

        assertThatThrownBy(() -> balanceService.save(balanceDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Amount must be greater than zero");
        verify(balanceRepository, never()).save(any(BalanceModel.class));
    }

    @Test
    void shouldThrowBadRequestExceptionWhenInsertBalanceDateIsGreaterThanToday() {
        BalanceDto balanceDto = new BalanceDto(10.00, "Description", TypeEnum.CREDIT, LocalDate.now().plusDays(1), null);

        assertThatThrownBy(() -> balanceService.save(balanceDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Date cannot be greater than the current year");
        verify(balanceRepository, never()).save(any(BalanceModel.class));
    }

    @Test
    void shouldThrowBadRequestExceptionWhenInsertBalanceDescriptionIsGreaterThan50() {
        var description = "a".repeat(51);
        BalanceDto balanceDto = new BalanceDto(10.00, description, TypeEnum.CREDIT, LocalDate.now(), null);

        assertThatThrownBy(() -> balanceService.save(balanceDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Description must be less than 50 characters");
        verify(balanceRepository, never()).save(any(BalanceModel.class));
    }

    @Test
    void shouldThrowBadRequestExceptionWhenInsertBalanceDescriptionIsEmpty() {
        var description = "a".repeat(51);
        BalanceDto balanceDto = new BalanceDto(10.00, "", TypeEnum.CREDIT, LocalDate.now(), null);

        assertThatThrownBy(() -> balanceService.save(balanceDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Description must be less than 50 characters");
        verify(balanceRepository, never()).save(any(BalanceModel.class));
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
    void shouldUpdateBalance() {
        User userEntity = UserFixture.userDefaultEntityUserRole;
        BalanceDto balanceDto = BalanceFixture.balanceDtoUpdate;
        BalanceModel balanceModel = BalanceFixture.balanceUpdated;
        var balanceDtoResponse = balanceMapper.convertBalanceToBalanceDto(balanceModel);

        when(balanceRepository.findById("1")).thenReturn(Optional.of(balanceModel));
        when(service.getUser()).thenReturn(userEntity);
        when(balanceRepository.save(balanceModel)).thenReturn(balanceModel);

        var result = balanceService.updateBalance("1", balanceDto);

        assertThat(result).isEqualTo(balanceDtoResponse);
        verify(balanceRepository).save(balanceModel);
    }

    @Test
    void shouldThrowExceptionWhenUpdateBalanceHasDifferentUser() {
        BalanceDto balanceDto = BalanceFixture.balanceDtoUpdate;
        BalanceModel balanceModel = BalanceFixture.balanceUpdated;

        when(balanceRepository.findById("1")).thenReturn(Optional.of(balanceModel));
        when(service.getUser()).thenReturn(UserFixture.userDefaultEntityUserRoleId2);

        assertThatThrownBy(() -> balanceService.updateBalance("1", balanceDto))
                .isInstanceOf(ForbiddenException.class)
                .hasMessageContaining("User email@email.com cannot update balance for another user");
    }

    @Test
    void shouldGetBalanceByMonthRangePagedWhenRoleIsUser() throws DataFormatException {
        User userEntity = UserFixture.userDefaultUserRole;
        var pageable= BalanceFixture.geraPageRequest(0,10, Sort.Direction.DESC);
        var pagedBalanceEntity = BalanceFixture.geraPageBalance();

        when(service.getUser()).thenReturn(userEntity);
        when(balanceRepository.findBalanceModelByDate(pageable, "2022-01-01", "2024-10-30", userEntity.getId())).thenReturn(pagedBalanceEntity);
        var result = balanceService.getBalanceByMonthRange(pageable, "2022-01-01", "2024-10-30");

        assertFalse(result.isEmpty());
        assertThat(service.getUser().getId()).isEqualTo(userEntity.getId());
        verify(balanceRepository).findBalanceModelByDate(pageable, "2022-01-01", "2024-10-30", userEntity.getId());
    }

    @Test
    void shouldGetBalanceByMonthRangePagedWhenRoleIsAdmin() throws DataFormatException {
        User userEntity = UserFixture.userDefaultUserAdmin;
        var pageable= BalanceFixture.geraPageRequest(0,10, Sort.Direction.DESC);
        var pagedBalanceEntity = BalanceFixture.geraPageBalance();

        when(service.getUser()).thenReturn(userEntity);
        when(balanceRepository.findBalanceModelByDate(pageable, "2022-01-01", "2024-10-30")).thenReturn(pagedBalanceEntity);
        var result = balanceService.getBalanceByMonthRange(pageable, "2022-01-01", "2024-10-30");

        assertFalse(result.isEmpty());
        assertThat(service.getUser().getId()).isEqualTo(userEntity.getId());
        verify(balanceRepository).findBalanceModelByDate(pageable, "2022-01-01", "2024-10-30");
    }

    @Test
    void shouldThrowExceptionWhenIdNotExists() {
        String balanceId = "1";

        when(balanceRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> balanceService.getBalanceById(balanceId))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessageContaining("Id 1 not found.");
        verify(balanceRepository).findById(balanceId);
    }

    @Test
    void shouldThrowExceptionWhenIdNotExistsOnUpdateBalance() {
        String balanceId = "1";

        when(balanceRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> balanceService.getBalanceById(balanceId))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessageContaining("Id 1 not found.");
        verify(balanceRepository).findById(balanceId);
    }

    @Test
    void shouldGetBalanceTotalWithAdminRoleUser() throws DataFormatException {
        when(service.getUser()).thenReturn(UserFixture.userDefaultUserAdmin);
        when(balanceRepository.getBalanceTotal("2024-01-01", "2024-01-02")).thenReturn(1.0);

        var result = balanceService.getBalanceTotal("2024-01-01", "2024-01-02");

        assertThat(result).isEqualTo("1,00");
    }

    @Test
    void shouldGetBalanceTotalWithUserRole() throws DataFormatException {
        when(service.getUser()).thenReturn(UserFixture.userDefaultEntityUserRole);
        when(balanceRepository.getBalanceTotal("2024-01-01", "2024-01-02", "1")).thenReturn(1.0);

        var result = balanceService.getBalanceTotal("2024-01-01", "2024-01-02");

        assertThat(result).isEqualTo("1,00");
    }

    @Test
    void shouldGetBalanceTotalWithAdminRoleAndWithoutDate() throws DataFormatException {
        when(service.getUser()).thenReturn(UserFixture.userDefaultUserAdmin);
        when(balanceRepository.getBalanceTotal(null, null)).thenReturn(1.0);

        var result = balanceService.getBalanceTotal(null, null);

        assertThat(result).isEqualTo("1,00");
    }

    @Test
    void shouldGetBalanceTotalWithUserRoleAndWithoutDate() throws DataFormatException {
        when(service.getUser()).thenReturn(UserFixture.userDefaultEntityUserRole);
        when(balanceRepository.getBalanceTotal(null, null, "1")).thenReturn(1.0);

        var result = balanceService.getBalanceTotal(null, null);

        assertThat(result).isEqualTo("1,00");
    }

}
