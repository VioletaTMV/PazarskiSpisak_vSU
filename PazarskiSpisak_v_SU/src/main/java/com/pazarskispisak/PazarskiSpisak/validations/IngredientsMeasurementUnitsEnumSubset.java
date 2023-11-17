package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IngredientsMeasurementUnitsEnumSubsetValidator.class)
public @interface IngredientsMeasurementUnitsEnumSubset {

    IngredientMeasurementUnitEnum[] anyOf();
    String message() default "must be any of {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
