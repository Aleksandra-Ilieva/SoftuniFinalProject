package org.example.softunifinalproject.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.softunifinalproject.validation.TimeInWorkingHoursValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeInWorkingHoursValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface  TimeInWorkingHours {
    String message() default "You cannot reserve non-working hours!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
