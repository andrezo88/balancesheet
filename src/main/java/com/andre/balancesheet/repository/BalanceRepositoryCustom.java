package com.andre.balancesheet.repository;

import com.andre.balancesheet.model.BalanceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.zip.DataFormatException;

public interface BalanceRepositoryCustom {

    Page<BalanceModel> findBalanceModelByDate(Pageable pageable, String startDate, String endDate, String id) throws DataFormatException;
    Page<BalanceModel> findBalanceModelByDate(Pageable pageable, String startDate, String endDate) throws DataFormatException;

    Page<BalanceModel> findBalanceModelByDate(Pageable pageable) throws DataFormatException;

    Page<BalanceModel> findBalanceByUserId(Pageable pageable, String id);

    Double getBalanceTotal(Pageable pageable, String startDate, String endDate) throws DataFormatException;

    Double getBalanceTotal(Pageable pageable, String startDate, String endDate, String id) throws DataFormatException;
}
