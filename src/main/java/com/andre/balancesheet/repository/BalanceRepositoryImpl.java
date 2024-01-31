package com.andre.balancesheet.repository;

import com.andre.balancesheet.model.BalanceModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.zip.DataFormatException;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@AllArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepositoryCustom {
    public static final int DIFERENCE_DATES = 1;
    public static final int NO_DIFERENCE_DATE = 0;
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<BalanceModel> findBalanceModelByDate(Pageable pageable, String startDate, String endDate, String id) throws DataFormatException {

        Query query = new Query();

        if(Objects.nonNull(id)) {
            query.addCriteria(where("userId").is(id));
        }

        startDate = getDate(startDate, DIFERENCE_DATES);

        endDate = getDate(endDate, NO_DIFERENCE_DATE);

        if(Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            query.addCriteria(where("date").gte(LocalDate.parse(startDate)).lte(LocalDate.parse(endDate)));
        }

        List<BalanceModel> balanceModels = mongoTemplate.find(query, BalanceModel.class);
        return new PageImpl<>(balanceModels, pageable, pageable.getPageSize());

    }

    private static String getDate(String startDate, int days) throws DataFormatException {
        if(Objects.isNull(startDate)) {
            startDate = LocalDate.now().minusDays(days).toString();
        } else {
            try {
                LocalDate.parse(startDate);
            } catch (Exception e) {
                throw new DataFormatException(String.format("Invalid date format: %s. Must be yyyy-MM-dd", startDate));
            }
        }
        return startDate;
    }

    @Override
    public Page<BalanceModel> findBalanceModelByDate(Pageable pageable, String startDate, String endDate) throws DataFormatException {

        return findBalanceModelByDate(pageable, startDate, endDate, null);
    }

    @Override
    public Page<BalanceModel> findBalanceByUserId(Pageable pageable, String id) {

        Query query = new Query();

        query.addCriteria(where("userId").is(id));

        List<BalanceModel> balanceModels = mongoTemplate.find(query, BalanceModel.class);
        return new PageImpl<>(balanceModels, pageable, pageable.getPageSize());
    }

    @Override
    public Page<BalanceModel> findAllPaged(Pageable pageable) {

        Query query = new Query();

        List<BalanceModel> balanceModels = mongoTemplate.find(query, BalanceModel.class);
        return new PageImpl<>(balanceModels, pageable, pageable.getPageSize());
    }
}
