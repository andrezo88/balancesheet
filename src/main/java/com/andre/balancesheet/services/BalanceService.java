package com.andre.balancesheet.services;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.dtos.BalanceDtoResponse;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.models.BalanceModel;
import com.andre.balancesheet.repositories.BalanceRepository;
import com.andre.balancesheet.utils.mappers.BalanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Page<BalanceDtoResponse> getBalacePaged(Pageable pageable) {
        var balanceList = balanceRepository.findAll(pageable);
        return balanceList.map(mapper::convertBalanceToBalanceDto);
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

    public Page<BalanceDtoResponse> getBalanceByMonth(Pageable pageable, String monthNumber) {
        var balanceList = balanceRepository.findByMonth(pageable, monthNumber);
        balanceList.filter(balance -> balance.getDate().getMonthValue() == Integer.parseInt(monthNumber));
        return balanceList.map(mapper::convertBalanceToBalanceDto);
    }

    public BalanceDto isLateEntry( BalanceDto dto) {
        if (Objects.isNull(dto.getDate()))
            dto.setDate(LocalDate.now());
        return dto;
    }
}
