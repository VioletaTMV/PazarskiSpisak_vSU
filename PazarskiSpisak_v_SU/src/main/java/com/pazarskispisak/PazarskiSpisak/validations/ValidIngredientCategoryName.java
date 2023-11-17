package com.pazarskispisak.PazarskiSpisak.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidIngredientCategoryNameValidator.class)
public @interface ValidIngredientCategoryName {

    String message() default "Category not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
