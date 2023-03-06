package com.truenorth.calculator.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.truenorth.calculator.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserRecordDTO {

    private Long id;
    private String operationName;

    @JsonIgnore
    private OperationType operationType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")

    private LocalDateTime dateInserted;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateDeleted;

    private String operationResponse;

    private Double userBalance;

    private Double amount;

    private Boolean deleted;

    private Boolean success;

}
