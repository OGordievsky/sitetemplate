package ru.acme.web.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class NewMenuValidator implements ConstraintValidator<NewMenu, List<String>> {
    private static final String URL_REGEX = "\\D+";

    @Override
    public void initialize(NewMenu constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (!value.isEmpty()) {
            for (String str : value) {
                if (!str.matches(URL_REGEX)) {
                    return false;
                }
            }
        }
        return true;
    }
}
