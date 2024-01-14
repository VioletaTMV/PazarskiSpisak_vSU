package com.pazarskispisak.PazarskiSpisak.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = ShopListDisplMUMainOrAltOrEmptyValidator.class)
public @interface ShopListDisplMUMainOrAltOrEmpty {

//    String first();
//    String[] second();
//    String third();

    String message() default "Invalid value for field. Must be one of the already used or empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
