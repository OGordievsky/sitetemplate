package ru.acme.web.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = NewImgValidator.class)
public @interface NewImage {
    String message() default
            "Uploading image has contain data encoded Base64 with headers -> imgUrl : [\"data:image/...,/\"] || "
                    + "Or should be empty if set -> No image";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NewImage[] value();
    }
}
