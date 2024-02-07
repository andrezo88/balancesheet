package com.andre.balancesheet.repository;

import com.andre.balancesheet.model.Token;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@AllArgsConstructor
public class TokenRepositoryImpl implements TokenRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Token> findAllValidTokensByUser(String userId) {

        Query query = new Query();

        query.addCriteria(where("userId").is(userId));

        query.addCriteria(where("expired").is(false));

        query.addCriteria(where("revoked").is(false));

        return mongoTemplate.find(query, Token.class);
    }

    @Override
    public Optional<Token> findByToken(String token) {

        Query query = new Query();

        query.addCriteria(where("token").is(token));

        return Optional.ofNullable(mongoTemplate.findOne(query, Token.class));
    }


}
