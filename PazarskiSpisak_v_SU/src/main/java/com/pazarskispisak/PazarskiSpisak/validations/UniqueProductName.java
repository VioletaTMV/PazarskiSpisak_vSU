package com.pazarskispisak.PazarskiSpisak.validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueProductNameValidator.class)
public @interface UniqueProductName {

    String message() default "Product already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
