package com.andre.balancesheet.repositories;

import com.andre.balancesheet.repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    void shouldFindAllValidTokensByUser() {

        var result = tokenRepository.findAllValidTokensByUser("1");

        assertThat(result).isNotNull();
    }

    @Test
    void shouldFindTokenByToken() {

        var result = tokenRepository.findByToken("1");

        assertThat(result).isNotNull();
    }
}
