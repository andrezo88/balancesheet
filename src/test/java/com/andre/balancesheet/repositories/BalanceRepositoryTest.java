package com.andre.balancesheet.repositories;

import com.andre.balancesheet.fixtures.BalanceFixture;
import com.andre.balancesheet.model.BalanceModel;
import com.andre.balancesheet.model.TypeEnum;
import com.andre.balancesheet.repository.BalanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.zip.DataFormatException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
                .hasFieldOrPropertyWithValue("date", LocalDate.now())
                .hasFieldOrPropertyWithValue("createdAt", LocalDateTime.parse("2024-01-01T10:00:00"))
                .isNotNull();
    }

    @Test
    void shouldFindBalanceByDateWithId() throws DataFormatException {

        var pageable= BalanceFixture.geraPageRequest(0,10, Sort.Direction.DESC);

        var result = balanceRepository.findBalanceModelByDate(pageable,"2024-01-01", "2024-01-02", "1");

        assertThat(result.getContent());
    }

    @Test
    void shouldFindBalanceByDateWithoutId() throws DataFormatException {

        var pageable= BalanceFixture.geraPageRequest(0,10, Sort.Direction.DESC);

        var result = balanceRepository.findBalanceModelByDate(pageable,"2024-01-01", "2024-01-02");

        assertThat(result.getContent()).isNotNull();
    }

    @Test
    void shoudlFindBalanceByDateWithIncorrectDate() throws DataFormatException {
        var pageable= BalanceFixture.geraPageRequest(0,10, Sort.Direction.DESC);

        assertThatThrownBy(() -> balanceRepository.findBalanceModelByDate(pageable,"01-01", "2024-01-02"))
                .isInstanceOf(DataFormatException.class)
                .hasMessageContaining("Invalid date format: %s. Must be yyyy-MM-dd", "01-01");
    }

    @Test
    void shouldFindBalanceByDateWithoutDate() throws DataFormatException {

        var pageable= BalanceFixture.geraPageRequest(0,10, Sort.Direction.DESC);

        var result = balanceRepository.findBalanceModelByDate(pageable, null, null);

        assertThat(result.getContent());
    }

    @Test
    void shouldFindBalanceById() throws DataFormatException {

        var pageable= BalanceFixture.geraPageRequest(0,10, Sort.Direction.DESC);

        var result = balanceRepository.findBalanceByUserId(pageable,"1");

        assertThat(result.getContent());
    }
}
