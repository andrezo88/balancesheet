package com.andre.balancesheet.services;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.dtos.BalanceDtoResponse;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.models.BalanceModel;
import com.andre.balancesheet.repositories.BalanceRepository;
import com.andre.balancesheet.utils.mappers.BalanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BalanceService {


    private final BalanceMapper mapper;

    private final BalanceRepository balanceRepository;

    public BalanceDtoResponse save(BalanceDto dto) {
        dto = isLateEntry(dto);
        BalanceModel saved = balanceRepository.save(mapper.convertBalanceDtoToBalance(dto));
        return mapper.convertBalanceToBalanceDto(saved);
    }

    public List<BalanceDtoResponse> getBalance() {
        return mapper.convertListBalanceToListBalanceDtoResponse(balanceRepository.findAll());
    }

    public BalanceDtoResponse getBalanceById(String balanceId) {
        return mapper.convertBalanceToBalanceDto(balanceRepository.findById(balanceId)
                .orElseThrow(() -> new IdNotFoundException(
                        String.format("Id %s not found: ",balanceId))
                )
        );
    }

    public BalanceDtoResponse updateBalance(String balanceId, BalanceDto dto) {
        BalanceModel balance = balanceRepository.findById(balanceId)
                .orElseThrow(() -> new IdNotFoundException(
                        String.format("Id %s not found: ", balanceId))
                );
        balance.setAmount(dto.getAmount());
        balance.setDescription(dto.getDescription());
        balance.setType(dto.getType());
        balance.setDate(dto.getDate());
        balanceRepository.save(balance);
        return mapper.convertBalanceToBalanceDto(balance);
    }

    public List<BalanceDtoResponse> getBalanceByMonth(String monthNumber) {
        var balanceList = balanceRepository.findAll();
        var balanceListByMonth = balanceList.stream()
                .filter(balance -> balance.getDate().getMonth().toString().equals(monthNumber))
                .toList();
        return mapper.convertListBalanceToListBalanceDtoResponse(balanceListByMonth);
    }

    public BalanceDto isLateEntry( BalanceDto dto) {
        if (Objects.isNull(dto.getDate()))
            dto.setDate(LocalDate.now());
        return dto;
    }
}
