package com.andre.balancesheet.fixtures;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.model.BalanceModel;
import com.andre.balancesheet.model.TypeEnum;
import com.andre.balancesheet.util.mapper.BalanceMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

public class BalanceFixture {

    public static final String URL_BALANCE = "/api/v1/balance";

    public static final BalanceMapper INSTANCE_MAPPER = Mappers.getMapper(BalanceMapper.class);

    public static final BalanceModel balanceDefault = BalanceModel.builder()
            .amount(100.0)
            .description("lunch")
            .type(TypeEnum.DEBIT)
            .date(LocalDate.now())
            .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
            .userId("1234")
            .build();

    public static final BalanceDto balanceDefaultDto = BalanceDto.builder()
            .amount(100.0)
            .description("lunch")
            .type(TypeEnum.DEBIT)
            .date(LocalDate.now())
            .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
            .build();

    public static final BalanceDto balanceLateEntryDto = BalanceDto.builder()
            .amount(100.0)
            .description("lunch")
            .type(TypeEnum.DEBIT)
            .date(LocalDate.parse("2021-10-10"))
            .build();

   public static final BalanceDtoResponse balanceDtoResponse = BalanceDtoResponse.builder()
            .id("1")
            .amount(100.0)
            .description("lunch")
            .type(TypeEnum.DEBIT)
            .date(LocalDate.parse("2021-10-10"))
            .build();

    public static PageRequest geraPageRequest(int page,
                                              int size,
                                              Sort.Direction direction){
        return PageRequest.of(page,size,Sort.by(new Sort.Order(direction,"id")));
    }

    public static Page<BalanceModel> geraPageBalance(){
        return new PageImpl<>(List.of(BalanceModel.builder()
                    .id("1")
                    .amount(100.00)
                    .description("lunch")
                    .type(TypeEnum.CREDIT)
                    .date(LocalDate.parse("2021-10-10"))
                .build()),geraPageRequest(0,10,DESC),1);
    }

    public static Page<BalanceDtoResponse> geraPageBalanceDto(){
        return new PageImpl<>(List.of(BalanceDtoResponse.builder()
                .id("1")
                .amount(100.00)
                .description("lunch")
                .type(TypeEnum.CREDIT)
                .date(LocalDate.parse("2021-10-10"))
                .build()),geraPageRequest(0,10, DESC),1);
    }



    public static final BalanceDto balanceDtoUpdate = BalanceDto.builder()
           .amount(150.0)
           .description("lunch")
           .type(TypeEnum.CASH)
           .date(LocalDate.parse("2024-01-02"))
           .build();

    public static final BalanceDtoResponse balanceDtoResponseUpdate = BalanceDtoResponse.builder()
            .id("1")
            .amount(150.0)
            .description("lunch")
            .type(TypeEnum.CASH)
            .date(LocalDate.parse("2024-01-02"))
            .build();

    public static final BalanceModel balanceUpdated = BalanceModel.builder()
            .id("1")
            .amount(150.0)
            .description("lunch")
            .type(TypeEnum.CASH)
            .date(LocalDate.parse("2024-01-02"))
            .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
            .build();


}
