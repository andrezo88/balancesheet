package com.andre.balancesheet.util.mapper;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.model.BalanceModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-16T15:03:56-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class BalanceMapperImpl implements BalanceMapper {

    @Override
    public BalanceModel convertBalanceDtoToBalance(BalanceDto balanceDto) {
        if ( balanceDto == null ) {
            return null;
        }

        BalanceModel.BalanceModelBuilder balanceModel = BalanceModel.builder();

        balanceModel.amount( balanceDto.getAmount() );
        balanceModel.description( balanceDto.getDescription() );
        balanceModel.type( balanceDto.getType() );
        balanceModel.date( balanceDto.getDate() );

        return balanceModel.build();
    }

    @Override
    public BalanceDtoResponse convertBalanceToBalanceDto(BalanceModel balanceModel) {
        if ( balanceModel == null ) {
            return null;
        }

        BalanceDtoResponse.BalanceDtoResponseBuilder balanceDtoResponse = BalanceDtoResponse.builder();

        balanceDtoResponse.id( balanceModel.getId() );
        balanceDtoResponse.amount( balanceModel.getAmount() );
        balanceDtoResponse.description( balanceModel.getDescription() );
        balanceDtoResponse.type( balanceModel.getType() );
        balanceDtoResponse.date( balanceModel.getDate() );

        return balanceDtoResponse.build();
    }

    @Override
    public List<BalanceDtoResponse> convertListBalanceToListBalanceDtoResponse(List<BalanceModel> balance) {
        if ( balance == null ) {
            return null;
        }

        List<BalanceDtoResponse> list = new ArrayList<BalanceDtoResponse>( balance.size() );
        for ( BalanceModel balanceModel : balance ) {
            list.add( convertBalanceToBalanceDto( balanceModel ) );
        }

        return list;
    }
}
