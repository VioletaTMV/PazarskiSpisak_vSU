package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.validations.FloatPositiveOrNullWithMax2DecimalDigits;
import com.pazarskispisak.PazarskiSpisak.validations.ValidIngredient;
import com.pazarskispisak.PazarskiSpisak.validations.ValueOfEnum;
import jakarta.validation.constraints.*;

import java.util.*;

public class RecipeIngredientWithDetailsAddDTO {

    @NotNull(message = "Задължително поле.")
    @ValidIngredient(message = "Навалидна съставка")
    private Long ingredientId;

    @NotNull(message = "Задължително поле.")
    @ValueOfEnum(enumClass = IngredientMeasurementUnitEnum.class, message = "Невалидна м. ед.")
    private String ingredientMeasurementUnitEnum;

    @NotNull(message = "Невалидно к-во.")
    @FloatPositiveOrNullWithMax2DecimalDigits(message = "Макс. 2 знака след запетайката")
    @Max(value = 99999, message = "Под 100 000.")
    private Float qty;

    private Map<IngredientMeasurementUnitEnum, String> allowedIngredientMeasurementUnitEnumForProductMap;

    public RecipeIngredientWithDetailsAddDTO() {
        this.allowedIngredientMeasurementUnitEnumForProductMap = new LinkedHashMap<>();
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public RecipeIngredientWithDetailsAddDTO setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
        return this;
    }

    public String getIngredientMeasurementUnitEnum() {
        return ingredientMeasurementUnitEnum;
    }

    public RecipeIngredientWithDetailsAddDTO setIngredientMeasurementUnitEnum(String ingredientMeasurementUnitEnum) {
        this.ingredientMeasurementUnitEnum = ingredientMeasurementUnitEnum;
        return this;
    }

    public Float getQty() {
        return qty;
    }

    public RecipeIngredientWithDetailsAddDTO setQty(Float qty) {
        this.qty = qty;
        return this;
    }

    public Map<IngredientMeasurementUnitEnum, String> getAllowedIngredientMeasurementUnitEnumForProductMap() {
        return allowedIngredientMeasurementUnitEnumForProductMap;
    }

    public RecipeIngredientWithDetailsAddDTO setAllowedIngredientMeasurementUnitEnumForProductMap(Map<IngredientMeasurementUnitEnum, String> allowedIngredientMeasurementUnitEnumForProductMap) {
        this.allowedIngredientMeasurementUnitEnumForProductMap = allowedIngredientMeasurementUnitEnumForProductMap;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientWithDetailsAddDTO that = (RecipeIngredientWithDetailsAddDTO) o;
        return Objects.equals(getIngredientId(), that.getIngredientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredientId());
    }
}
