package org.example.softunifinalproject.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.softunifinalproject.validation.annotation.DateInWorkingDays;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateInWorkingDaysValidator implements ConstraintValidator<DateInWorkingDays, LocalDate> {


    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }
        return true;
    }
}
