package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.models.entities.Recipe;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.Optional;

public class UniqueRecipeNameForUserValidator implements ConstraintValidator<UniqueRecipeNameForUser, String> {

    private RecipeService recipeService;
    private HttpServletRequest httpServletRequest;

    @Autowired
    public UniqueRecipeNameForUserValidator(RecipeService recipeService, HttpServletRequest httpServletRequest) {
        this.recipeService = recipeService;
        this.httpServletRequest = httpServletRequest;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Principal principal = this.httpServletRequest.getUserPrincipal();
        String emailCurrentUser = principal.getName();

        Optional<Recipe> byRecipeNameAndCurrentlyLoggedUserOpt = this.recipeService.findByRecipeNameAndCurrentlyLoggedUser(value, emailCurrentUser);

        if (byRecipeNameAndCurrentlyLoggedUserOpt.isPresent()){
            return false;
        }

        return true;
    }
}
