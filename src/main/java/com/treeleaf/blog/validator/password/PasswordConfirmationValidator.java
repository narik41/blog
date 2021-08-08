package com.treeleaf.blog.validator.password;

import com.treeleaf.blog.register.UserRegisterRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmationValidator implements ConstraintValidator<PasswordConfirmation, Object> {

    @Override
    public void initialize(PasswordConfirmation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){

        UserRegisterRequest user = (UserRegisterRequest) obj;
        return user.getPassword().equals(user.getPasswordConfirmation());
    }
}
