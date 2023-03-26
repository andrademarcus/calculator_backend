package com.demo.calculator.request;

import com.demo.calculator.enums.OperationType;
import com.demo.calculator.validation.ConditionalValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ConditionalValidation(
        conditionalProperty = "operationType", values = {"OperationType.ADDITION", "OperationType.SUBSTRACTION", "OperationType.MULTIPLICATION", "OperationType.DIVISION"},
        requiredProperties = {"number1", "number2"},
        message = "Number 1 and Number 2 are required for selected operation")
@ConditionalValidation(
        conditionalProperty = "operationType", values = {"OperationType.SQUARE_ROOT"},
        requiredProperties = {"number1"},
        message = "Number 1 is required for selected operation.")
public class OperationFormRequest {

    @NotNull(message = "Operation Type is required")
    private OperationType operationType;

    private Double number1;
    private Double number2;

}
