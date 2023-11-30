package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.validations.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import jakarta.validation.constraints.*;

import java.util.LinkedHashMap;
import java.util.Map;

@ShoppingListDisplayMeasurementUnitForProductMatchOneOfMainOrAlternativeUnitsChosenOrEmpty(
        message = "Мерната единица в пазарски списък трябва да е или избраната основна мерна единица, или една от алтернативните мерни единици, приложими (попълнени) за продукта."
)
public class IngredientDTO {

    private Long id;

    @NotBlank(message = "Задължително поле.")
    @Size(min = 2, max = 50, message = "Името на продукта трябва да бъде между 2 и 50 символа.")
    @UniqueProductName(message = "Продукта вече съществува.")
    private String name;

    @NotBlank(message = "Задължително поле.")
    @ValidIngredientCategoryName(message = "Невалиден избор - изберете някоя от предложените опции.")
    private String itemCategorySupermarketName;

    @NotNull(message = "Задължително поле.")
    @IngredientsMeasurementUnitsEnumSubset(anyOf = {IngredientMeasurementUnitEnum.GRAM, IngredientMeasurementUnitEnum.MILLILITER}, message = "Изберете между гр. или мл.")
    private IngredientMeasurementUnitEnum mainUnitOfMeasurement;

    private Map<IngredientMeasurementUnitEnum,
        @FloatPositiveOrNullWithMax2DecimalDigits(message = "К-вото трябва да е положително число, с до 2 символа след десетичния знак. Ако съответната алт.м.ед. не е приложима за продукта оставете полето празно.")
                Float> ingredientAltMUVMap;

    @NotNull(message = "Задължително поле.")
    private IngredientMeasurementUnitEnum shoppingListDisplayUnitOfMeasurement;


    public IngredientDTO() {
        this.ingredientAltMUVMap = new LinkedHashMap<>();
    }

    public Long getId() {
        return id;
    }

    public IngredientDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public IngredientDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getItemCategorySupermarketName() {
        return itemCategorySupermarketName;
    }

    public IngredientDTO setItemCategorySupermarketName(String itemCategorySupermarketName) {
        this.itemCategorySupermarketName = itemCategorySupermarketName;
        return this;
    }

    public IngredientMeasurementUnitEnum getMainUnitOfMeasurement() {
        return mainUnitOfMeasurement;
    }

    public IngredientDTO setMainUnitOfMeasurement(IngredientMeasurementUnitEnum mainUnitOfMeasurement) {
        this.mainUnitOfMeasurement = mainUnitOfMeasurement;
        return this;
    }

    public IngredientMeasurementUnitEnum getShoppingListDisplayUnitOfMeasurement() {
        return shoppingListDisplayUnitOfMeasurement;
    }

    public IngredientDTO setShoppingListDisplayUnitOfMeasurement(IngredientMeasurementUnitEnum shoppingListDisplayUnitOfMeasurement) {
        this.shoppingListDisplayUnitOfMeasurement = shoppingListDisplayUnitOfMeasurement;
        return this;
    }

    public Map<IngredientMeasurementUnitEnum, Float> getIngredientAltMUVMap() {
        return ingredientAltMUVMap;
    }

    public IngredientDTO setIngredientAltMUVMap(Map<IngredientMeasurementUnitEnum, Float> ingredientAltMUVMap) {
        this.ingredientAltMUVMap = ingredientAltMUVMap;
        return this;
    }
}