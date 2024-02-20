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

import static com.andre.balancesheet.util.constant.IntConstants.DIFFERENCE_DATES;
import static com.andre.balancesheet.util.constant.IntConstants.NO_DIFFERENCE_DATE;
import static com.andre.balancesheet.util.constant.StringsConstants.USER_ID;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@AllArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<BalanceModel> findBalanceModelByDate(Pageable pageable, String startDate, String endDate, String id) throws DataFormatException {

        List<BalanceModel> balanceModels = getBalanceModels(startDate, endDate, id);
        return new PageImpl<>(balanceModels, pageable, pageable.getPageSize());

    }

    private List<BalanceModel> getBalanceModels(String startDate, String endDate, String id) throws DataFormatException {
        Query query = new Query();

        if(Objects.nonNull(id)) {
            query.addCriteria(where(USER_ID.getDescription()).is(id));
        }

        startDate = getDate(startDate, DIFFERENCE_DATES.getValue());

        endDate = getDate(endDate, NO_DIFFERENCE_DATE.getValue());

        if(Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            query.addCriteria(where("date").gte(LocalDate.parse(startDate)).lte(LocalDate.parse(endDate)));
        }

        return mongoTemplate.find(query, BalanceModel.class);
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
    public Page<BalanceModel> findBalanceModelByDate(Pageable pageable) throws DataFormatException {

        return findBalanceModelByDate(pageable, null, null, null);
    }

    @Override
    public Page<BalanceModel> findBalanceByUserId(Pageable pageable, String id) {

        Query query = new Query();

        query.addCriteria(where(USER_ID.getDescription()).is(id));

        List<BalanceModel> balanceModels = mongoTemplate.find(query, BalanceModel.class);
        return new PageImpl<>(balanceModels, pageable, pageable.getPageSize());
    }

    @Override
    public Double getBalanceTotal(String startDate, String endDate, String id) throws DataFormatException {

        List<BalanceModel> balanceModels = getBalanceModels(startDate, endDate, id);
        return balanceModels.stream().mapToDouble(BalanceModel::getAmount).sum();
    }
    @Override
    public Double getBalanceTotal(String startDate, String endDate) throws DataFormatException {

        return getBalanceTotal(startDate, endDate, null);
    }


}
