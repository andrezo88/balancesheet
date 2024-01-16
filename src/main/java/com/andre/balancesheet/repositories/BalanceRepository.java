package com.andre.balancesheet.repositories;

import com.andre.balancesheet.models.BalanceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface BalanceRepository extends MongoRepository<BalanceModel, String>, BalanceRepositoryCustom {

    Page<BalanceModel> findAll(Pageable pageable);

}