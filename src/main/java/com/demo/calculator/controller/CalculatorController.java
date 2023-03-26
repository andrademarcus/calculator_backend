package com.demo.calculator.controller;

import com.demo.calculator.enums.OperationType;
import com.demo.calculator.request.OperationFormRequest;
import com.demo.calculator.service.CalculatorService;
import com.demo.calculator.exception.ValidationException;
import com.demo.calculator.response.OperationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/operations")
public class CalculatorController {

    private final CalculatorService calculatorService;

    private final MessageSource messageSource;

    public CalculatorController(CalculatorService calculatorService, MessageSource messageSource) {
        this.calculatorService = calculatorService;
        this.messageSource = messageSource;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @Operation(summary = "Retrieve all operations", security = @SecurityRequirement(name = "bearer-token"))
    public Map<String, String> getOperations(Locale locale) {

        final Map<String, String> results = Arrays.stream(OperationType.values())
                .collect(Collectors.toMap(e -> e.getValue(),
                        e -> messageSource.getMessage(e.toString(), null, locale)));

        return results.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/calculator")
    @Operation(summary = "Execute a operation", security = @SecurityRequirement(name = "bearer-token"))
    public OperationResponse calculator(@RequestBody @Valid OperationFormRequest operation,
                                        BindingResult errors) {

        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }

        return calculatorService.calculate(operation);

    }

}
