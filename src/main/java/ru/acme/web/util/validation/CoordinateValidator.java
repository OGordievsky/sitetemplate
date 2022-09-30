package ru.acme.web.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CoordinateValidator implements ConstraintValidator<Coordinate, Double[]> {
    @Override
    public void initialize(Coordinate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double[] value, ConstraintValidatorContext context) {
        if (value == null){
            return true;
        }
        if (value.length == 2){
            return -90.0 <= value[0] && value[0] <= 90.0 && -180.0 <= value[1] && value[1] <= 180.0;
        }
        return false;
    }
}
