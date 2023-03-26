package com.demo.calculator.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationResponse {

    private Double userBalance;

    private Double operationResults;

    private String operationResultsString;

    private Double operationCost;

    private String message;

}
