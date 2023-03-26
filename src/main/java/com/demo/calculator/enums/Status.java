package com.demo.calculator.enums;

public enum Status {
    ACTIVE(1), INACTIVE(0);

    Integer value;
    Status(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }


}
