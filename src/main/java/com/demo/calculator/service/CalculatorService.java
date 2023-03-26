package com.demo.calculator.service;

import com.demo.calculator.request.OperationFormRequest;
import com.demo.calculator.response.OperationResponse;

public interface CalculatorService {

    OperationResponse calculate(OperationFormRequest operation);

}
