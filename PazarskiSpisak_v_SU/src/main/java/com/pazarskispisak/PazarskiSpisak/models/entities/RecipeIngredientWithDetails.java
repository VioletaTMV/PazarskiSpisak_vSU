package com.pazarskispisak.PazarskiSpisak.models.entities;


import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredientWithDetails {

    @EmbeddedId
    private RecipeIngredientID recipeIngredientID;

    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    @MapsId("recipeId")
    private Recipe recipe;

    @ManyToOne(targetEntity = Ingredient.class)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @Enumerated(EnumType.STRING)
    @Column(name = "ingredient_measurement_unit_used", nullable = false)
    private IngredientMeasurementUnitEnum ingredientMeasurementUnitUsed;

    @Column(name = "ingredient_measurement_total_value", nullable = false)
    private Double ingredientMeasurementTotalValue;

    public RecipeIngredientWithDetails() {
    }

//    public RecipeIngredientWithDetails(RecipeIngredientID recipeIngredientID, IngredientMeasurementUnitEnum ingredientMeasurementUnitUsed, Double ingredientMeasurementTotalValue) {
//        this.recipeIngredientID = recipeIngredientID;
//        this.ingredientMeasurementUnitUsed = ingredientMeasurementUnitUsed;
//        this.ingredientMeasurementTotalValue = ingredientMeasurementTotalValue;
//    }

    public RecipeIngredientWithDetails(Recipe recipe, Ingredient ingredient, IngredientMeasurementUnitEnum ingredientMeasurementUnitUsedInRecipe,
                                       Double ingredientMeasurementTotalValue) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.ingredientMeasurementUnitUsed = ingredientMeasurementUnitUsedInRecipe;
        this.ingredientMeasurementTotalValue = ingredientMeasurementTotalValue;
        this.recipeIngredientID = new RecipeIngredientID(recipe.getId(), ingredient.getId());

    }

    public RecipeIngredientID getRecipeIngredientsID() {
        return recipeIngredientID;
    }

    public void setRecipeIngredientsID(RecipeIngredientID recipeIngredientID) {
        this.recipeIngredientID = recipeIngredientID;
    }

    public IngredientMeasurementUnitEnum getIngredientMeasurementUnitUsed() {
        return ingredientMeasurementUnitUsed;
    }

    public void setIngredientMeasurementUnitUsed(IngredientMeasurementUnitEnum ingredientMeasurementUnitUsed) {
        this.ingredientMeasurementUnitUsed = ingredientMeasurementUnitUsed;
    }

    public Double getIngredientMeasurementTotalValue() {
        return ingredientMeasurementTotalValue;
    }

    public void setIngredientMeasurementTotalValue(Double ingredientMeasurementTotalValue) {
        this.ingredientMeasurementTotalValue = ingredientMeasurementTotalValue;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public RecipeIngredientWithDetails setRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public RecipeIngredientWithDetails setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    @Override
    public String toString() {
        return "RecipeIngredientWithDetails{" +
                "recipeIngredientID=" + recipeIngredientID +
                ", ingredient=" + ingredient +
                ", ingredientMeasurementUnitUsed=" + ingredientMeasurementUnitUsed +
                ", ingredientMeasurementTotalValue=" + ingredientMeasurementTotalValue +
                '}';
    }
}
