package com.andre.balancesheet.repositories;

import com.andre.balancesheet.models.BalanceModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@AllArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepositoryCustom {


    private final MongoTemplate mongoTemplate;

    @Override
    public Page<BalanceModel> findBalanceModelByDate(Pageable pageable, String startDate, String endDate) {

        Query query = new Query();

        query.addCriteria(where("date").gte(LocalDate.parse(startDate)).lte(LocalDate.parse(endDate)));

        List<BalanceModel> balanceModels = mongoTemplate.find(query, BalanceModel.class);
        var resultPage = new PageImpl<>(balanceModels, pageable, pageable.getPageSize());
        return resultPage;

    }
}
