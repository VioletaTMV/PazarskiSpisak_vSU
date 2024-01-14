package com.pazarskispisak.PazarskiSpisak.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE_USE})
@Constraint(validatedBy = FloatPosOrNullWMax2DecDigValidator.class)
public @interface FloatPositiveOrNullWithMax2DecimalDigits {

    String message() default "Invalid value for input field float";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
