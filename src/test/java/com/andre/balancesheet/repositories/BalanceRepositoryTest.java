package com.andre.balancesheet.repositories;

import com.andre.balancesheet.fixtures.BalanceFixture;
import com.andre.balancesheet.models.BalanceModel;
import com.andre.balancesheet.models.TypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
class BalanceRepositoryTest {

    @Autowired
    private BalanceRepository balanceRepository;


    @Test
    void shouldInsertBalance() {
        BalanceModel balanceModel = BalanceFixture.balanceDefault;

        var result = balanceRepository.save(balanceModel);

        assertThat(result)
                .hasFieldOrPropertyWithValue("id", result.getId())
                .hasFieldOrPropertyWithValue("amount", 100.00)
                .hasFieldOrPropertyWithValue("description", "lunch")
                .hasFieldOrPropertyWithValue("type", TypeEnum.DEBIT)
                .hasFieldOrPropertyWithValue("isLateEntry", false)
                .hasFieldOrPropertyWithValue("date", LocalDate.now())
                .hasFieldOrPropertyWithValue("createdAt", LocalDateTime.parse("2024-01-01T10:00:00"))
                .isNotNull();
    }
}
