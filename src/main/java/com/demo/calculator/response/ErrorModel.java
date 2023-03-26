package com.demo.calculator.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorModel {

    private final List<String> messages;

    @JsonCreator
    public ErrorModel(@JsonProperty("messages") List<String> messages) {
        this.messages = new ArrayList<>(messages);
    }

    public ErrorModel(String message) {
        this.messages = Collections.singletonList(message);
    }

    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }

}
