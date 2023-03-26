package com.demo.calculator.enums.converter;

import com.demo.calculator.enums.OperationType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OperationTypeConverter implements AttributeConverter<OperationType, String> {

    @Override
    public String convertToDatabaseColumn(OperationType type) {
        if (type == null) {
            return null;
        }
        return type.getValue();
    }

    @Override
    public OperationType convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(OperationType.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
