package ru.acme.web.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NewImgValidator implements ConstraintValidator<NewImage, String> {

    @Override
    public void initialize(NewImage constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null){
            return true;
        }
        return value.startsWith("data:image") || value.isEmpty();
    }
}
