package com.andre.balancesheet.repository;

import com.andre.balancesheet.model.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepositoryCustom {

    List<Token> findAllValidTokensByUser(String userId);

    Optional<Token> findByToken(String token);
}
