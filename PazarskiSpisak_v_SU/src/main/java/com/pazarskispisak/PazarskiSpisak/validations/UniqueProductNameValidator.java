package com.pazarskispisak.PazarskiSpisak.validations;

import com.pazarskispisak.PazarskiSpisak.service.ItemService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    private final ItemService itemService;

    @Autowired
    public UniqueProductNameValidator(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.itemService.findByName(value).isEmpty();
    }
}
