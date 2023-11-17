package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
import com.pazarskispisak.PazarskiSpisak.models.entities.Recipe;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ValidRecipeValidator implements ConstraintValidator<ValidRecipe, Long> {

    private RecipeService recipeService;

    @Autowired
    public ValidRecipeValidator(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        Optional<Recipe> byIdOpt = this.recipeService.findById(value);

        return byIdOpt.isPresent();
    }
}
