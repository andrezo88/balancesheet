package com.andre.balancesheet.repositories;

import com.andre.balancesheet.models.BalanceModel;
import com.andre.balancesheet.models.TypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class BalanceRepositoryTest {

    @Autowired
    private BalanceRepository balanceRepository;


    @Test
    void shouldInsertBalance() {
        BalanceModel balanceModel = BalanceModel.builder()
                .id("1")
                .amount(100.0)
                .description("lunch")
                .type(TypeEnum.DEBIT)
                .isLateEntry(false)
                .date(LocalDate.parse("2024-01-01"))
                .createdAt(LocalDateTime.parse("2024-01-01T10:00:00"))
                .build();

        var result = balanceRepository.save(balanceModel);

        assertThat(result)
                .hasFieldOrPropertyWithValue("id", result.getId())
                .hasFieldOrPropertyWithValue("amount", 100.00)
                .hasFieldOrPropertyWithValue("description", "lunch")
                .hasFieldOrPropertyWithValue("type", TypeEnum.DEBIT)
                .hasFieldOrPropertyWithValue("isLateEntry", false)
                .hasFieldOrPropertyWithValue("date", LocalDate.parse("2024-01-01"))
                .hasFieldOrPropertyWithValue("createdAt", LocalDateTime.parse("2024-01-01T10:00:00"))
                .isNotNull();
    }
}
