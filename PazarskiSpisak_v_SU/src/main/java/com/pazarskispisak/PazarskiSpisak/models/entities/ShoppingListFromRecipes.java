package com.pazarskispisak.PazarskiSpisak.models.entities;

import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "shopping_list_from_recipes")
public class ShoppingListFromRecipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "cooker_id", referencedColumnName = "id", nullable = false)
    private User cooker;

    @ElementCollection
    @CollectionTable(name="shop_list_recipes_desired_servings_mapping", joinColumns={ @JoinColumn(name="shop_list_id", referencedColumnName = "id") })
    @MapKeyJoinColumn(name="recipe_id")
    @Column(name="desired_servings")
    private Map<Recipe, Short> recipesSelectedWithDesiredServingsMap;

    @ElementCollection
    @CollectionTable(name="shop_list_ingredients_bought_mapping", joinColumns={ @JoinColumn(name="shop_list_id", referencedColumnName = "id") })
    @MapKeyJoinColumn(name="ingredient_id")
    @Column(name="is_bought")
    private Map<Ingredient, Boolean> ingredientsPurchaseStatusMap;

    @Column(name = "pure_alphabet_order")
    private Boolean pureAlphabetOrdering;

    @Column(name = "hide_bought")
    private Boolean hideBought;

    @Column(name = "last_accessed_date")
    private LocalDate lastAccessedDate;


    public ShoppingListFromRecipes() {
        this.recipesSelectedWithDesiredServingsMap = new LinkedHashMap<>();
        this.ingredientsPurchaseStatusMap = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public User getCooker() {
        return cooker;
    }

    public ShoppingListFromRecipes setCooker(User cooker) {
        this.cooker = cooker;
        return this;
    }

    public Map<Recipe, Short> getRecipesSelectedWithDesiredServingsMap() {
        return recipesSelectedWithDesiredServingsMap;
    }

    public ShoppingListFromRecipes setRecipesSelectedWithDesiredServingsMap(Map<Recipe, Short> recipesSelectedWithDesiredServingsMap) {
        this.recipesSelectedWithDesiredServingsMap = recipesSelectedWithDesiredServingsMap;
        return this;
    }

    public Map<Ingredient, Boolean> getIngredientsPurchaseStatusMap() {
        return ingredientsPurchaseStatusMap;
    }

    public ShoppingListFromRecipes setIngredientsPurchaseStatusMap(Map<Ingredient, Boolean> ingredientsPurchaseStatusMap) {
        this.ingredientsPurchaseStatusMap = ingredientsPurchaseStatusMap;
        return this;
    }

    public Boolean getPureAlphabetOrdering() {
        return pureAlphabetOrdering;
    }

    public ShoppingListFromRecipes setPureAlphabetOrdering(Boolean pureAlphabetOrdering) {
        this.pureAlphabetOrdering = pureAlphabetOrdering;
        return this;
    }

    public Boolean getHideBought() {
        return hideBought;
    }

    public ShoppingListFromRecipes setHideBought(Boolean hideBought) {
        this.hideBought = hideBought;
        return this;
    }

    public LocalDate getLastAccessedDate() {
        return lastAccessedDate;
    }

    public ShoppingListFromRecipes setLastAccessedDate(LocalDate lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
        return this;
    }
}
