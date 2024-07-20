package org.example.softunifinalproject.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.softunifinalproject.service.UserService;
import org.example.softunifinalproject.validation.annotation.UniqueEmail;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        return userService.validateEmail(email);
    }
}
