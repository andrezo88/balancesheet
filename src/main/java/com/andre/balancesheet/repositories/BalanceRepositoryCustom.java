package com.andre.balancesheet.repositories;

import com.andre.balancesheet.models.BalanceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BalanceRepositoryCustom {

    Page<BalanceModel> findBalanceModelByDate(Pageable pageable, String startDate, String endDate);
}
