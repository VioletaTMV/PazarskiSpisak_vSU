package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class IngredientsMeasurementUnitsEnumSubsetValidator implements ConstraintValidator<IngredientsMeasurementUnitsEnumSubset, IngredientMeasurementUnitEnum> {

    private IngredientMeasurementUnitEnum[] subset;

    @Override
    public void initialize(IngredientsMeasurementUnitsEnumSubset constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(IngredientMeasurementUnitEnum value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
