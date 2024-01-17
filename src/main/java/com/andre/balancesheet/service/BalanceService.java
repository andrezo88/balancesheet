package com.andre.balancesheet.service;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.model.BalanceModel;
import com.andre.balancesheet.repository.BalanceRepository;
import com.andre.balancesheet.util.mapper.BalanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.andre.balancesheet.util.validation.BalanceValidation.*;

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

    public Page<BalanceDtoResponse> getBalancePaged(Pageable pageable) {
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

    public String nameUser(){
        var user = SecurityContextHolder.getContext().getAuthentication();
        return user.getName();
    }

}
