package com.demo.calculator.enums;

public enum OperationType {

    ADDITION("ADDITION"), SUBSTRACTION("SUBSTRACTION"), MULTIPLICATION("MULTIPLICATION"),
    DIVISION("DIVISION"), SQUARE_ROOT("SQUARE_ROOT"), RANDOM_STRING("RANDOM_STRING");

    String value;
    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getDeclaringClass().getSimpleName() + "." + name();
    }

}
