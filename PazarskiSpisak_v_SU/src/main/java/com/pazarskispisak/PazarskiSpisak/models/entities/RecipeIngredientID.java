package com.pazarskispisak.PazarskiSpisak.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecipeIngredientID implements Serializable {

//    @ManyToOne(targetEntity = Recipe.class)
//    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
//    private Recipe recipe;
//
//    @ManyToOne(targetEntity = Ingredient.class)
//    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
//    private Ingredient ingredient;
//
//    public RecipeIngredientsID(){}
//
//    public RecipeIngredientsID(Recipe recipe, Ingredient ingredient) {
//        this.recipe = recipe;
//        this.ingredient = ingredient;
//    }
//
//    public Recipe getRecipe() {
//        return recipe;
//    }
//
//    public Ingredient getIngredient() {
//        return ingredient;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        RecipeIngredientsID that = (RecipeIngredientsID) o;
//        return Objects.equals(getRecipe(), that.getRecipe()) && Objects.equals(getIngredient(), that.getIngredient());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getRecipe(), getIngredient());
//    }

//    @Column(name = "recipe_id")
    private Long recipeId;

//    @Column(name = "ingredient_id")
    private Long ingredientId;

    public RecipeIngredientID(){}

    public RecipeIngredientID(Long recipeId, Long ingredientId) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public RecipeIngredientID setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
        return this;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public RecipeIngredientID setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientID that = (RecipeIngredientID) o;
        return Objects.equals(getRecipeId(), that.getRecipeId()) && Objects.equals(getIngredientId(), that.getIngredientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRecipeId(), getIngredientId());
    }
}
