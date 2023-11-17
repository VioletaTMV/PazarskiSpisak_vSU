package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ValidIngredientValidator implements ConstraintValidator <ValidIngredient, Long>{

    private final IngredientService ingredientService;

    @Autowired
    public ValidIngredientValidator(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        Optional<Ingredient> byIdOpt = this.ingredientService.findById(value);

        return byIdOpt.isPresent();

    }
}
