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

import static com.andre.balancesheet.utils.validation.BalanceValidation.*;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceMapper mapper;

    private final BalanceRepository balanceRepository;

    public BalanceDtoResponse save(BalanceDto dto) {
        amountVerifier(dto);
        descriptionVerifier(dto);
        dateVerifier(dto);
        isLateEntry(dto);
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

    public Page<BalanceDtoResponse> getBalanceByMonth(Pageable pageable, String startDate, String endDate) {
        var balanceList = balanceRepository.findBalanceModelByDate(pageable, startDate, endDate);
        return balanceList.map(mapper::convertBalanceToBalanceDto);
    }
}
