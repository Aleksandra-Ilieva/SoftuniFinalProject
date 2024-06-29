package org.example.softunifinalproject.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.softunifinalproject.validation.DateInWorkingDaysValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateInWorkingDaysValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateInWorkingDays {
    String message() default "You cannot book on weekends!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
