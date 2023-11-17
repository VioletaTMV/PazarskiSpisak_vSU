package com.pazarskispisak.PazarskiSpisak.models.dtos;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class RecipeIngredientWithDetailsDTO {

    @NotBlank
    private String ingredientName;

    @NotBlank
    private String measureBG;

    @NotNull
    @Min(0)
    @Max(99999)
    private String qty;

    public RecipeIngredientWithDetailsDTO() {
    }

    public RecipeIngredientWithDetailsDTO(String ingredientName, String measureBG, String qty) {
        this.ingredientName = ingredientName;
        this.measureBG = measureBG;
        this.qty = qty;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public RecipeIngredientWithDetailsDTO setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
        return this;
    }

    public String getMeasureBG() {
        return measureBG;
    }

    public RecipeIngredientWithDetailsDTO setMeasureBG(String measureBG) {
        this.measureBG = measureBG;
        return this;
    }

    public String getQty() {
        return qty;
    }

    public RecipeIngredientWithDetailsDTO setQty(String qty) {
        this.qty = qty;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientWithDetailsDTO that = (RecipeIngredientWithDetailsDTO) o;
        return Objects.equals(getIngredientName(), that.getIngredientName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredientName());
    }
}
