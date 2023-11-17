package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidNonFoodItemCategoryNameValidator implements ConstraintValidator<ValidNonFoodItemCategoryName, String> {

    private ItemCategoryService itemCategoryService;

    @Autowired
    public ValidNonFoodItemCategoryNameValidator(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        ItemCategorySupermarket currentItemCategoryChosen = this.itemCategoryService.findByName(value).orElse(null);

        boolean isNonFood = currentItemCategoryChosen != null && !currentItemCategoryChosen.getFood();

        return isNonFood;

    }
}
