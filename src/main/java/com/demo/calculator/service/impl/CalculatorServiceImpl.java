package com.demo.calculator.service.impl;

import com.demo.calculator.service.UserRecordService;
import com.demo.calculator.exception.BusinessException;
import com.demo.calculator.exception.OperationException;
import com.demo.calculator.persistence.model.Operation;
import com.demo.calculator.persistence.model.UserRecord;
import com.demo.calculator.persistence.repository.OperationRepository;
import com.demo.calculator.persistence.repository.UserRecordRepository;
import com.demo.calculator.persistence.repository.UserRepository;
import com.demo.calculator.request.OperationFormRequest;
import com.demo.calculator.response.OperationResponse;
import com.demo.calculator.service.AuthenticationFacade;
import com.demo.calculator.service.CalculatorService;
import com.demo.calculator.util.RandomClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private static Logger logger = LoggerFactory.getLogger(CalculatorServiceImpl.class);

    private final UserRepository userRepository;
    private final UserRecordRepository userRecordRepository;

    private final OperationRepository operationRepository;

    private final UserRecordService userRecordService;

    private final MessageSource messageSource;

    private final RandomClient randomClient;

    private final AuthenticationFacade authenticationFacade;

    public CalculatorServiceImpl(UserRepository userRepository,
                                 UserRecordRepository userRecordRepository,
                                 OperationRepository operationRepository,
                                 UserRecordService userRecordService,
                                 MessageSource messageSource,
                                 RandomClient randomClient,
                                 AuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.userRecordRepository = userRecordRepository;
        this.operationRepository = operationRepository;
        this.messageSource = messageSource;
        this.userRecordService = userRecordService;
        this.randomClient = randomClient;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public OperationResponse calculate(OperationFormRequest operationRequest) throws BusinessException {

        // get user balance
        UserRecord lastUserRecord = userRecordService.getLastUserRecord()
                .orElseThrow(() -> new BusinessException("User has no balance."));

        // get operation cost
        Operation operation = operationRepository.findByType(operationRequest.getOperationType())
                .orElseThrow(() -> new OperationException("Operation not found: " + operationRequest.getOperationType().toString() + ". Contact the administrator."));

        // check balance x cost
        double operationCost = operation.getCost();
        double userBalance = lastUserRecord.getUserBalance();

        // new user record
        UserRecord newUserRecord = new UserRecord();
        newUserRecord.setDate(LocalDateTime.now());
        newUserRecord.setUser(lastUserRecord.getUser());
        newUserRecord.setUserBalance(lastUserRecord.getUserBalance());
        newUserRecord.setOperation(operation);
        newUserRecord.setAmount(operationCost);

        if (operationCost > userBalance) {
            newUserRecord.setOperationResponse("Insufficient balance");
            newUserRecord.setSuccess(false);
            saveUserRecord(newUserRecord);
            throw new BusinessException("Insufficient balance. Current user balance is " + userBalance +". Operation cost: " + operationCost);
        }

        final double finalUserBalance = userBalance - operationCost;

        OperationResponse response = new OperationResponse();

        Double operationResults = null;

        try {

            switch (operationRequest.getOperationType()) {
                case ADDITION:
                    operationResults = ((operationRequest.getNumber1() + operationRequest.getNumber2()));
                    break;
                case SUBSTRACTION:
                    operationResults = ((operationRequest.getNumber1() - operationRequest.getNumber2()));
                    break;
                case MULTIPLICATION:
                    operationResults = ((operationRequest.getNumber1() * operationRequest.getNumber2()));
                    break;
                case DIVISION:
                    operationResults = ((operationRequest.getNumber1() / operationRequest.getNumber2()));
                    break;
                case SQUARE_ROOT:
                    operationResults = (Math.sqrt(operationRequest.getNumber1().doubleValue()));
                    break;
                case RANDOM_STRING:
                    response.setOperationResultsString(getRandomString());
                    break;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }

        // save user record
        newUserRecord.setOperationResponse(operationResults != null ? operationResults.toString() : response.getOperationResultsString());
        newUserRecord.setSuccess(true);
        newUserRecord.setUserBalance(finalUserBalance);
        userRecordRepository.save(newUserRecord);

        // user view response
        response.setUserBalance(finalUserBalance);
        response.setOperationCost(operationCost);
        response.setOperationResults(operationResults);

        // custom message
        if (response.getOperationResultsString() == null) {
            response.setMessage(messageSource.getMessage("calculator.number.success",
                    new Object[]{
                            operationRequest.getOperationType().getValue(),
                            operationRequest.getNumber1(),
                            operationRequest.getNumber2(),
                            newUserRecord.getOperationResponse(),
                            operationCost,
                            finalUserBalance,
                    }, Locale.getDefault()));
        } else {
            response.setMessage(messageSource.getMessage("calculator.string.success",
                    new Object[]{
                            operationRequest.getOperationType().getValue(),
                            newUserRecord.getOperationResponse(),
                            operationCost,
                            finalUserBalance,
                    }, Locale.getDefault()));
        }

        return response;

    }

    /**
     * New transaction to avoid rollback before a thrown exception
     *
     * @param userRecord
     */
    @Transactional
    private void saveUserRecord(UserRecord userRecord) {
        userRecordRepository.save(userRecord);
    }

    private String getRandomString() throws IOException, InterruptedException {
        return randomClient.getRandomString();
    }

}

