package com.andre.balancesheet.util.mapper;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.model.BalanceModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
public interface BalanceMapper {

    BalanceModel convertBalanceDtoToBalance(BalanceDto balanceDto);

    BalanceDtoResponse convertBalanceToBalanceDto(BalanceModel balanceModel);

    BalanceModel convertBalanceDtoToBalanceUpdate(BalanceDto balanceDto, @MappingTarget BalanceModel balanceModel);

}
