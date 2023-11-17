package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.validations.UniqueProductName;
import com.pazarskispisak.PazarskiSpisak.validations.ValidNonFoodItemCategoryName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ItemDTO {

    @NotBlank(message = "Задължително поле.")
    @Size(min = 2, max = 50, message = "Името на продукта трябва да бъде между 2 и 50 символа.")
    @UniqueProductName(message = "Продукта вече съществува.")
    private String name;

    @NotBlank(message = "Задължително поле.")
    @ValidNonFoodItemCategoryName(message = "Невалиден избор - изберете някоя от предложените опции.")
    private String itemCategorySupermarketName;

    public ItemDTO(){}

    public String getName() {
        return name;
    }

    public ItemDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getItemCategorySupermarketName() {
        return itemCategorySupermarketName;
    }

    public ItemDTO setItemCategorySupermarketName(String itemCategorySupermarketName) {
        this.itemCategorySupermarketName = itemCategorySupermarketName;
        return this;
    }
}
