package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientDTO;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopListDisplMUMainOrAltOrEmptyValidator implements ConstraintValidator
        <ShopListDisplMUMainOrAltOrEmpty, IngredientDTO> {

    private IngredientMeasurementUnitEnum mainUnitChosen;
    private List<IngredientMeasurementUnitEnum> alternativeUnitsNotNullAndPositive;
    private IngredientMeasurementUnitEnum shoppingListDisplayUnit;
    private String message;

//    private String first;
//    private String[] second;
//    private String third;
//    private String message;

    @Override
    public void initialize(ShopListDisplMUMainOrAltOrEmpty constraintAnnotation) {

//        this.first = constraintAnnotation.first();
//        this.second = constraintAnnotation.second();
//        this.third = constraintAnnotation.third();
        this.message = constraintAnnotation.message();

    }

    @Override
    public boolean isValid(IngredientDTO ingredientDTO, ConstraintValidatorContext context) {

        boolean valid = false;

        mainUnitChosen = ingredientDTO.getMainUnitOfMeasurement();
        alternativeUnitsNotNullAndPositive = ingredientDTO.getIngredientAltMUVMap().entrySet().stream()
                .filter(e -> e.getValue() != null && e.getValue() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (ingredientDTO.getShoppingListDisplayUnitOfMeasurement() == null) {
            valid = false;
        } else if (
                ingredientDTO.getShoppingListDisplayUnitOfMeasurement().equals(mainUnitChosen) ||
                        alternativeUnitsNotNullAndPositive.contains(ingredientDTO.getShoppingListDisplayUnitOfMeasurement()) ||
                        ingredientDTO.getShoppingListDisplayUnitOfMeasurement().equals(IngredientMeasurementUnitEnum.EMPTY)) {
            valid = true;
        }

        if (!valid) {
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("shoppingListDisplayUnitOfMeasurement")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
