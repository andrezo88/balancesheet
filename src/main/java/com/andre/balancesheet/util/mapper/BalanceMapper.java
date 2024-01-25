package com.andre.balancesheet.util.mapper;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.model.BalanceModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    BalanceModel convertBalanceDtoToBalance(BalanceDto balanceDto);

    BalanceDtoResponse convertBalanceToBalanceDto(BalanceModel balanceModel);

}
