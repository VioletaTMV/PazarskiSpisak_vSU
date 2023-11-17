package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.validations.UniqueProductCategoryName;
import jakarta.validation.constraints.*;

public class ItemCategorySupermarketAddDTO {

    @NotBlank(message = "Задължително поле.")
    @Size(min = 2, max = 50, message = "Името на продуктовата категория трябва да е м/у 2 и 50 символа.")
    @UniqueProductCategoryName(message = "Продуктовата категория вече съществува.")
    private String name;

    @NotNull(message = "Изборът на категория продукт е задължителен.")
    @Min(value = 0, message = "Моля изберете едната от възможните опции.")
    @Max(value = 1, message = "Моля изберете едната от възможните опции.")
    private Integer isFood;

    public ItemCategorySupermarketAddDTO() {
    }

    public String getName() {
        return name;
    }

    public ItemCategorySupermarketAddDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getIsFood() {
        return isFood;
    }

    public ItemCategorySupermarketAddDTO setIsFood(Integer isFood) {
        this.isFood = isFood;
        return this;
    }
}
