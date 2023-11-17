package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidIngredientCategoryNameValidator implements ConstraintValidator<ValidIngredientCategoryName, String> {

 private final ItemCategoryService itemCategoryService;

 @Autowired
    public ValidIngredientCategoryNameValidator(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        ItemCategorySupermarket currentItemCategoryChosen = this.itemCategoryService.findByName(value).orElse(null);

        return currentItemCategoryChosen != null ? currentItemCategoryChosen.getFood() : false;

    }
}
