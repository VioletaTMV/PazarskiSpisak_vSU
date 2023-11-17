package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;

import java.util.LinkedHashSet;
import java.util.Set;

public class RecipeViewDTO {

    private Long id;

    private String name;

    private String picture;

    private Short servings;

    private String cookSteps;

    private String notes;

    private boolean isVisible;

    private String dateLastModified;

    private String publishedBy;

    private String recipeSource;

    private String prepTime;

    private String cookTime;

    private Set<RecipeIngredientWithDetailsDTO> recipeIngredientWithDetailsDTOSet;

    private Set<RecipeCategoryEnum> recipeCategories;

    public RecipeViewDTO() {
    }

    public RecipeViewDTO(Long id, String name, String picture, Short servings, String cookSteps, String notes, boolean isVisible, String dateLastModified, String publishedBy, String recipeSource, String prepTime, String cookTime) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.servings = servings;
        this.cookSteps = cookSteps;
        this.notes = notes;
        this.isVisible = isVisible;
        this.dateLastModified = dateLastModified;
        this.publishedBy = publishedBy;
        this.recipeSource = recipeSource;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.recipeIngredientWithDetailsDTOSet = new LinkedHashSet<>();
    }

    public boolean isNotesPresent(){

        if (this.notes == null || this.notes.trim().isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isSourcePresent(){

        if (this.recipeSource == null || this.recipeSource.trim().isEmpty()){
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public Short getServings() {
        return servings;
    }

    public String getCookSteps() {
        return cookSteps;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String getRecipeSource() {
        return recipeSource;
    }

    public RecipeViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public RecipeViewDTO setName(String name) {
        this.name = name;
        return this;
    }

    public RecipeViewDTO setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public RecipeViewDTO setServings(Short servings) {
        this.servings = servings;
        return this;
    }

    public RecipeViewDTO setCookSteps(String cookSteps) {
        this.cookSteps = cookSteps;
        return this;
    }

    public RecipeViewDTO setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public RecipeViewDTO setVisible(boolean visible) {
        isVisible = visible;
        return this;
    }

    public RecipeViewDTO setRecipeSource(String recipeSource) {
        this.recipeSource = recipeSource;
        return this;
    }

    public String getDateLastModified() {
        return dateLastModified;
    }

    public RecipeViewDTO setDateLastModified(String dateLastModified) {
        this.dateLastModified = dateLastModified;
        return this;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public RecipeViewDTO setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
        return this;
    }

    public String getCookTime() {
        return cookTime;
    }

    public RecipeViewDTO setCookTime(String cookTime) {
        this.cookTime = cookTime;
        return this;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public RecipeViewDTO setPrepTime(String prepTime) {
        this.prepTime = prepTime;
        return this;
    }

    public Set<RecipeIngredientWithDetailsDTO> getRecipeIngredientWithDetailsDTOSet() {
        return recipeIngredientWithDetailsDTOSet;
    }

    public RecipeViewDTO setRecipeIngredientWithDetailsDTOSet(Set<RecipeIngredientWithDetailsDTO> recipeIngredientWithDetailsDTOSet) {
        this.recipeIngredientWithDetailsDTOSet = recipeIngredientWithDetailsDTOSet;
        return this;
    }

    public Set<RecipeCategoryEnum> getRecipeCategories() {
        return recipeCategories;
    }

    public RecipeViewDTO setRecipeCategories(Set<RecipeCategoryEnum> recipeCategories) {
        this.recipeCategories = recipeCategories;
        return this;
    }
}
