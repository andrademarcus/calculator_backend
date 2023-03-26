package com.demo.calculator.exception;

import com.demo.calculator.response.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler({ValidationException.class, BusinessException.class, OperationException.class})
    public ResponseEntity<ErrorModel> handle(Exception ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorModel(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorModel> handleUserNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorModel(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorModel> handleBadCredentials() {
        return ResponseEntity.badRequest()
                .body(new ErrorModel("Bad Credentials"));
    }

}
