package com.truenorth.calculator.exception;

public class RecordNotFoundException extends RuntimeException {
    
    public RecordNotFoundException(String id) {
        super("Could not find record " + id);
    }


}
