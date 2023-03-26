package com.demo.calculator.service;

import com.demo.calculator.persistence.model.UserRecord;
import com.demo.calculator.response.UserRecordPageDTO;
import com.demo.calculator.persistence.model.Operation;

import java.time.LocalDate;
import java.util.Optional;


public interface UserRecordService {

     UserRecordPageDTO findAll(Operation operation, LocalDate startDate,
                                     LocalDate endDate, String sortBy, String sortOrder, int page, int pageSize);

    void delete(Long id);

    Optional<UserRecord> getLastUserRecord();

}
