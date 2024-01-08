package com.andre.balancesheet.utils.mappers;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.dtos.BalanceDtoResponse;
import com.andre.balancesheet.models.BalanceModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    BalanceModel convertBalanceDtoToBalance(BalanceDto balanceDto);

    BalanceDtoResponse convertBalanceToBalanceDto(BalanceModel balanceModel);

    List<BalanceDtoResponse> convertListBalanceToListBalanceDtoResponse(List<BalanceModel> balance);
}
