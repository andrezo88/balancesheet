package com.andre.balancesheet.repository;

import com.andre.balancesheet.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface TokenRepository extends MongoRepository<Token, String>, TokenRepositoryCustom {

}
