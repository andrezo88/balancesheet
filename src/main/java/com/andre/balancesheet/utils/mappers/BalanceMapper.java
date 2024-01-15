package com.andre.balancesheet.utils.mappers;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.dtos.BalanceDtoResponse;
import com.andre.balancesheet.models.BalanceModel;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    BalanceModel convertBalanceDtoToBalance(BalanceDto balanceDto);

    BalanceDtoResponse convertBalanceToBalanceDto(BalanceModel balanceModel);

    List<BalanceDtoResponse> convertListBalanceToListBalanceDtoResponse(List<BalanceModel> balance);

    default Page<BalanceDtoResponse> convertPageBalanceToPageBalanceDtoResponse(Page<BalanceModel> balance) {
        return balance.map(this::convertBalanceToBalanceDto);
    }
}
