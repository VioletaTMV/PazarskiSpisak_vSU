package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class IngredientsMUEnumSubsetValidator implements ConstraintValidator<IngredientsMUEnumSubset, IngredientMeasurementUnitEnum> {

    private IngredientMeasurementUnitEnum[] subset;

    @Override
    public void initialize(IngredientsMUEnumSubset constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(IngredientMeasurementUnitEnum value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
