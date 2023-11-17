package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.validations.UniqueRecipeNameForUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.*;

public class RecipeAddDTO {

    @NotBlank(message = "Задължително поле.")
    @Size(min = 3, max = 75, message = "Името трябва да е с дължина между 3 и 75 символа.")
    @UniqueRecipeNameForUser(message = "Вече си публикувал рецепта с това име.")
    private String recipeName;

    @NotEmpty(message = "Избери съставки за рецептата.")
    @Size(min = 2, message = "Рецептата трябва да съдържа поне 2 съставки.")
    @UniqueElements(message = "Не може да се въвежда един продукт повече от веднъж в рецепта.")
    private List<@Valid RecipeIngredientWithDetailsAddDTO> recipeIngredientWithDetailsAddDTOList;

    //размера да не е повече от 25KB??
    private String picture;

    @NotNull(message = "Задължително поле.")
    @Min(value = 1, message = "Минимум 1 порция.")
    @Max(value = 99, message = "Броят порции трябва да бъде под 100.")
    private Short servings;

    @NotBlank(message = "Задължително поле.")
    @Size(max = 5000, message = "Не трябва да надхвърля 5000 символа.")
    private String cookSteps;

    @Size(max = 1000, message = "Не трябва да надхвърля 1000 символа.")
    private String notes;

    @Size(max = 255, message = "Не трябва да надхвърля 255 символа.")
    private String recipeSource;

    @Min(value = 0, message = ">= 0")
    @Max(value = 120, message = "<= 120")
    private Short prepTimeDD;

    @Min(value = 0, message = ">= 0")
    @Max(value = 23, message = "< 24")
    private Short prepTimeHH;

    @Min(value = 0, message = ">= 0")
    @Max(value = 59, message = "< 60")
    private Short prepTimeMM;

    @Min(value = 0, message = ">= 0")
    @Max(value = 23, message = "< 24")
    private Short cookTimeHH;

    @Min(value = 0, message = ">= 0")
    @Max(value = 59, message = "< 60")
    private Short cookTimeMM;

    @NotEmpty(message = "Избери поне една релевантна категория")
    private Set<String> categories;

    public RecipeAddDTO() {
        this.recipeIngredientWithDetailsAddDTOList = new ArrayList<>();
    }

    public String getRecipeName() {
        return recipeName;
    }

    public RecipeAddDTO setRecipeName(String recipeName) {
        this.recipeName = recipeName;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public RecipeAddDTO setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public Short getServings() {
        return servings;
    }

    public RecipeAddDTO setServings(Short servings) {
        this.servings = servings;
        return this;
    }

    public String getCookSteps() {
        return cookSteps;
    }

    public RecipeAddDTO setCookSteps(String cookSteps) {
        this.cookSteps = cookSteps;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public RecipeAddDTO setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public String getRecipeSource() {
        return recipeSource;
    }

    public RecipeAddDTO setRecipeSource(String recipeSource) {
        this.recipeSource = recipeSource;
        return this;
    }

    public Short getPrepTimeDD() {
        return prepTimeDD;
    }

    public RecipeAddDTO setPrepTimeDD(Short prepTimeDD) {
        this.prepTimeDD = prepTimeDD;
        return this;
    }

    public Short getPrepTimeHH() {
        return prepTimeHH;
    }

    public RecipeAddDTO setPrepTimeHH(Short prepTimeHH) {
        this.prepTimeHH = prepTimeHH;
        return this;
    }

    public Short getPrepTimeMM() {
        return prepTimeMM;
    }

    public RecipeAddDTO setPrepTimeMM(Short prepTimeMM) {
        this.prepTimeMM = prepTimeMM;
        return this;
    }

    public Short getCookTimeHH() {
        return cookTimeHH;
    }

    public RecipeAddDTO setCookTimeHH(Short cookTimeHH) {
        this.cookTimeHH = cookTimeHH;
        return this;
    }

    public Short getCookTimeMM() {
        return cookTimeMM;
    }

    public RecipeAddDTO setCookTimeMM(Short cookTimeMM) {
        this.cookTimeMM = cookTimeMM;
        return this;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public RecipeAddDTO setCategories(Set<String> categories) {
        this.categories = categories;
        return this;
    }

    public List<RecipeIngredientWithDetailsAddDTO> getRecipeIngredientWithDetailsAddDTOList() {
        return recipeIngredientWithDetailsAddDTOList;
    }

    public RecipeAddDTO setRecipeIngredientWithDetailsAddDTOList(List<RecipeIngredientWithDetailsAddDTO> recipeIngredientWithDetailsAddDTOList) {
        this.recipeIngredientWithDetailsAddDTOList = recipeIngredientWithDetailsAddDTOList;
        return this;
    }
}
