package com.truenorth.calculator.service;

import com.truenorth.calculator.persistence.model.UserRecord;
import com.truenorth.calculator.response.UserRecordPageDTO;
import com.truenorth.calculator.persistence.model.Operation;
import com.truenorth.calculator.persistence.model.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;


public interface UserRecordService {

     UserRecordPageDTO findAll(Operation operation, LocalDate startDate,
                                     LocalDate endDate, String sortBy, String sortOrder, int page, int pageSize);

    void delete(Long id);

    Optional<UserRecord> getLastUserRecord();

}
