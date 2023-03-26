package com.demo.calculator.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserRecordPageDTO {

    private List<UserRecordDTO> results = new ArrayList<>();
    private long totalElements = 0L;
    private int totalPages = 0;

    public UserRecordPageDTO(List<UserRecordDTO> userRecords, long totalElements, int totalPages) {
        this.results = userRecords;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
