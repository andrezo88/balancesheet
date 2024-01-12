package com.andre.balancesheet.fixtures;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.dtos.BalanceDtoResponse;
import com.andre.balancesheet.models.BalanceModel;
import com.andre.balancesheet.models.TypeEnum;
import com.andre.balancesheet.utils.mappers.BalanceMapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BalanceFixture {

    public static final String URL_BALANCE = "/v1/balance";

    public static final BalanceMapper INSTANCE_MAPPER = Mappers.getMapper(BalanceMapper.class);

    public static final BalanceModel balanceDefault = BalanceModel.builder()
            .amount(100.0)
            .description("lunch")
            .type(TypeEnum.DEBIT)
            .date(LocalDate.now())
            .build();

    public static final BalanceModel balanceDefaultLateEntry = BalanceModel.builder()
            .amount(100.0)
            .description("lunch")
            .type(TypeEnum.DEBIT)
            .date(LocalDate.parse("2021-10-10"))
            .build();

    public static final BalanceDto balanceDefaultDto = BalanceDto.builder()
            .amount(100.0)
            .description("lunch")
            .type(TypeEnum.DEBIT)
            .date(LocalDate.now())
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

   public static List<BalanceModel> listBalanceModel() {
       return List.of(
               BalanceModel.builder()
                       .id("1")
                       .amount(100.0)
                       .description("lunch")
                       .type(TypeEnum.DEBIT)
                       .isLateEntry(false)
                       .date(LocalDate.parse("2021-10-10"))
                       .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
                       .build(),
               BalanceModel.builder()
                       .id("2")
                       .amount(200.0)
                       .description("dinner")
                       .type(TypeEnum.DEBIT)
                       .isLateEntry(false)
                       .date(LocalDate.parse("2021-09-10"))
                       .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
                       .build()
       );
   }

   public static List<BalanceDtoResponse> listBalanceDtoResponse() {
       return List.of(
               BalanceDtoResponse.builder()
                       .id("1")
                       .amount(100.0)
                       .description("lunch")
                       .type(TypeEnum.DEBIT)
                       .date(LocalDate.parse("2021-10-10"))
                       .build(),
               BalanceDtoResponse.builder()
                       .id("2")
                       .amount(200.0)
                       .description("dinner")
                       .type(TypeEnum.DEBIT)
                       .date(LocalDate.parse("2021-10-10"))
                       .build()
       );
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
            .isLateEntry(false)
            .date(LocalDate.parse("2024-01-02"))
            .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
            .build();


}
