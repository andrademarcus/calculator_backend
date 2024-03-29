package com.demo.calculator.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Repeatable(Conditionals.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConditionalValidator.class)
public @interface ConditionalValidation {

    String message() default "Field is required.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String conditionalProperty();

    String[] values();

    String[] requiredProperties();
}
