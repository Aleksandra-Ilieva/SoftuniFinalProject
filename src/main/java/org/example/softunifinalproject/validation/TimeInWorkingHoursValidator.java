package org.example.softunifinalproject.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.softunifinalproject.validation.annotation.TimeInWorkingHours;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeInWorkingHoursValidator implements ConstraintValidator<TimeInWorkingHours, LocalTime> {




    @Override
    public boolean isValid(LocalTime time, ConstraintValidatorContext constraintValidatorContext) {
        if((time.isAfter(LocalTime.of(9,0)) || time.equals(LocalTime.of(9,0))) && time.isBefore(LocalTime.of(18,0)) ) {
            return true;
        }

        return false;
    }
}
