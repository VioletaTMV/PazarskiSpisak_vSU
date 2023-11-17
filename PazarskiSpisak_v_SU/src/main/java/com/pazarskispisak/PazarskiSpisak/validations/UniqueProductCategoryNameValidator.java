package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueProductCategoryNameValidator implements ConstraintValidator<UniqueProductCategoryName, String> {

    private final ItemCategoryService itemCategoryService;

    @Autowired
    public UniqueProductCategoryNameValidator(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.itemCategoryService.findByName(value).isEmpty();
    }
}
