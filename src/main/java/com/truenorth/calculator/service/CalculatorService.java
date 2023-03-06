package com.truenorth.calculator.service;

import com.truenorth.calculator.request.OperationFormRequest;
import com.truenorth.calculator.response.OperationResponse;

public interface CalculatorService {

    OperationResponse calculate(OperationFormRequest operation);

}
