package com.demo.calculator.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ConditionalValidator implements ConstraintValidator<ConditionalValidation, Object> {

    private  final Logger log = LoggerFactory.getLogger(getClass());

    private String conditionalProperty;
    private String[] requiredProperties;
    private String message;
    private String[] values;

    @Override
    public void initialize(ConditionalValidation constraint) {
        conditionalProperty = constraint.conditionalProperty();
        requiredProperties = constraint.requiredProperties();
        message = constraint.message();
        values = constraint.values();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        try {
            //Object conditionalPropertyValue = BeanUtils.getProperty(object, conditionalProperty);
            Object conditionalPropertyValue = new BeanWrapperImpl(object).getPropertyValue(conditionalProperty);
            System.out.println("@@@@ conditionalPropertyValue:" + conditionalPropertyValue);

            log.info("-----");
            log.info(">>>" + StringUtils.join(values, " , "));


            if (conditionalPropertyValue != null && doConditionalValidation(conditionalPropertyValue.toString())) {

                log.info("$$$$$ ");
                log.info("$$$$$ CONTAINS");

                return validateRequiredProperties(object, context);
            }
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            return false;
        }
        return true;
    }

    private boolean validateRequiredProperties(Object object, ConstraintValidatorContext context) throws IllegalAccessException, NoSuchMethodException {
        boolean isValid = true;
        for (String property : requiredProperties) {
            //Object requiredValue = BeanUtils.getProperty(object, property);
            Object requiredValue = new BeanWrapperImpl(object).getPropertyValue(property);
            System.out.println("@@@ VAL: " + requiredValue);
            boolean isPresent = requiredValue != null;
            if (!isPresent) {
                isValid = false;
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(property)
                        .addConstraintViolation();
            }
        }
        return isValid;
    }

    private boolean doConditionalValidation(Object actualValue) {
        return Arrays.asList(values).contains(actualValue);
    }
}
