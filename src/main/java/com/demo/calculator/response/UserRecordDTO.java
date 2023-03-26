package com.demo.calculator.response;

import com.demo.calculator.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
