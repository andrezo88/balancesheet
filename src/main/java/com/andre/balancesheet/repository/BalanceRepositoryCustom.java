package com.andre.balancesheet.repository;

import com.andre.balancesheet.model.BalanceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BalanceRepositoryCustom {

    Page<BalanceModel> findBalanceModelByDate(Pageable pageable, String startDate, String endDate, String id);

    Page<BalanceModel> findBalanceByUserId(Pageable pageable, String id);
}
