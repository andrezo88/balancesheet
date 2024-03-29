package com.andre.balancesheet.service;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.exceptions.service.ForbiddenException;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.model.BalanceModel;
import com.andre.balancesheet.model.User;
import com.andre.balancesheet.repository.BalanceRepository;
import com.andre.balancesheet.util.mapper.BalanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.zip.DataFormatException;

import static com.andre.balancesheet.util.validation.BalanceValidation.*;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceMapper mapper;

    private final BalanceRepository balanceRepository;

    private final AuthenticationService authenticationService;

    public BalanceDtoResponse save(BalanceDto dto) {
        amountVerifierNull(dto);
        amountVerifierNegative(dto);
        descriptionVerifier(dto);
        dateVerifier(dto);
        BalanceDto dtoNewDate = isLateEntry(dto);
        var entity = setUserId(dtoNewDate);
        BalanceModel saved = balanceRepository.save(entity);
        return mapper.convertBalanceToBalanceDto(saved);
    }

    public URI saveBalanceAndReturnURI(BalanceDto dto) {
        var saved = save(dto);
        return ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{balanceId}")
                .buildAndExpand(saved.id()).toUri();
    }

    public BalanceDtoResponse getBalanceById(String balanceId) {
        return mapper.convertBalanceToBalanceDto(balanceRepository.findById(balanceId)
                .orElseThrow(() -> new IdNotFoundException(
                        String.format("Id %s not found.",balanceId))
                )
        );
    }

    public BalanceDtoResponse updateBalance(String balanceId, BalanceDto dto) {
        var userId = authenticationService.getUser();
        BalanceModel balance = balanceRepository.findById(balanceId)
                .orElseThrow(() -> new IdNotFoundException(
                        String.format("Id %s not found.", balanceId))
                );
        isSameUser(dto, balance, userId);
        return mapper.convertBalanceToBalanceDto(balance);
    }

    private void isSameUser( BalanceDto dto,BalanceModel balance, User userId) {
        if(balance.getUserId().equals(userId.getId())) {
            balance.setAmount(dto.amount());
            balance.setDescription(dto.description());
            balance.setType(dto.type());
            balance.setDate(dto.date());
            balanceRepository.save(balance);
        } else {
            throw new ForbiddenException(
                    String.format("User %s cannot update balance for another user", authenticationService.getUser().getEmail())
            );
        }
    }

    public Page<BalanceDtoResponse> getBalanceByMonthRange(Pageable pageable, String startDate, String endDate) throws DataFormatException {
        var user = authenticationService.getUser();
        Page<BalanceModel> balanceList;
        if(isAdmin(user)) {
            balanceList = balanceRepository.findBalanceModelByDate(pageable, startDate, endDate, user.getId());
        } else {
            balanceList = balanceRepository.findBalanceModelByDate(pageable, startDate, endDate);
        }
        return balanceList.map(mapper::convertBalanceToBalanceDto);
    }


    private BalanceModel setUserId(BalanceDto dto) {
        var userId = authenticationService.getUser().getId();
        var entity = mapper.convertBalanceDtoToBalance(dto);
        entity.setUserId(userId);
        return entity;
    }

    private static boolean isAdmin(User user) {
        return user.getRole().getPermissions().stream().filter(p -> p.getPermission().startsWith("admin")).toList().isEmpty();
    }

    public String getBalanceTotal(Pageable pageable, String startDate, String endDate) throws DataFormatException {
        var user = authenticationService.getUser();
        Double total;
        if (isAdmin(user)) {
            total = balanceRepository.getBalanceTotal(pageable, startDate, endDate, user.getId());
        } else {
            total = balanceRepository.getBalanceTotal(pageable, startDate, endDate);
        }
        return String.format("%.2f", total);
    }
}
