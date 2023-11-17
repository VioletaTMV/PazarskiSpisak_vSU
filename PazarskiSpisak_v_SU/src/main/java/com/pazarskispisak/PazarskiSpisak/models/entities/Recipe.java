package com.pazarskispisak.PazarskiSpisak.models.entities;

import com.pazarskispisak.PazarskiSpisak.models.enums.*;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "recipes", uniqueConstraints = @UniqueConstraint(name = "UniqueNameAndPublishedBy", columnNames = {"name", "published_by"}))
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String picture;

    @Column(nullable = false)
    private Short servings;

    @Column(name = "cooking_steps", nullable = false,
            columnDefinition = "TEXT(5000)")
    private String cookSteps;

    @Column(columnDefinition = "TEXT(1000)")
    private String notes;

    @Column(name = "is_visible", nullable = false)
    private boolean isVisible;

    @Column(name = "date_published", nullable = false)
    private LocalDateTime datePublished;

    @Column(name = "date_modified", nullable = false)
    private LocalDateTime dateLastModified;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @ElementCollection(targetClass = RecipeCategoryEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_categories",
            joinColumns = @JoinColumn(name = "recipe_id"))
    @Enumerated(EnumType.STRING)
//    @Column(name = "category_name")
    private Set<RecipeCategoryEnum> recipeCategories;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "main_ingredient_type")
//    private RecipeMainIngredientType mainIngredientType;

    //с CascadeType Remove като изтрия рецепта се трият и RecipeIngredients табличните редове, но самите продукти си остават в Items
    //bez Cascade Type Remove хвръля грешка и не изтрива рецептата, зашото казва constraint violation
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE)
    private Set<RecipeIngredientWithDetails> recipeIngredientWithDetails;

    @ManyToOne(optional = false)
    @JoinColumn(name = "published_by", referencedColumnName = "id")
    private User publishedBy;

    @Column(name = "recipe_source")
    private String recipeSource;

//    @Column(name = "cooking_method")
//    @Enumerated(EnumType.STRING)
//    private CookingMethod cookingMethod;

//    @Column
//    @Enumerated(EnumType.STRING)
//    private Cuisine cuisine;

//    @Column
//    @Enumerated(EnumType.STRING)
//    private RecipeVariety variety;

    @Column(name = "prep_time")
    private Duration prepTime;

    @Column(name = "cook_time")
    private Duration cookTime;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "legacy_cook_id")
    private Integer legacyCookId;

    public Recipe() {
        this.isVisible = true;
        this.datePublished = LocalDateTime.now();
        this.dateLastModified = LocalDateTime.now();
        this.isDeleted = false;
        this.recipeCategories = new HashSet<>();
        this.viewCount = 0;
        this.recipeIngredientWithDetails = new LinkedHashSet<>();
    }

    public void addRecipeCategory(RecipeCategoryEnum recipeCategory) {
        this.recipeCategories.add(recipeCategory);
    }

    public void addRecipeIngredientWithDetails(Recipe recipe,
                                               Ingredient ingredient,
                                               IngredientMeasurementUnitEnum ingredientMeasurementUnitUsedInRecipe,
                                               Double ingredientMeasurementTotalValue){
        this.recipeIngredientWithDetails.add(new RecipeIngredientWithDetails(recipe, ingredient, ingredientMeasurementUnitUsedInRecipe, ingredientMeasurementTotalValue));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Short getServings() {
        return servings;
    }

    public void setServings(Short servings) {
        this.servings = servings;
    }

    public String getCookSteps() {
        return cookSteps;
    }

    public void setCookSteps(String cookSteps) {
        this.cookSteps = cookSteps;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public LocalDateTime getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(LocalDateTime datePublished) {
        this.datePublished = datePublished;
    }

    public LocalDateTime getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(LocalDateTime dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Set<RecipeCategoryEnum> getRecipeCategories() {
        return recipeCategories;
    }

    public void setRecipeCategories(Set<RecipeCategoryEnum> recipeCategories) {
        this.recipeCategories = recipeCategories;
    }

//    public Set<Ingredient> getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(Set<Ingredient> ingredients) {
//        this.ingredients = ingredients;
//    }
//
//    public RecipeMainIngredientType getMainIngredientType() {
//        return mainIngredientType;
//    }
//
//    public void setMainIngredientType(RecipeMainIngredientType mainIngredientType) {
//        this.mainIngredientType = mainIngredientType;
//    }

    public User getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(User publishedBy) {
        this.publishedBy = publishedBy;
    }

    public String getRecipeSource() {
        return recipeSource;
    }

    public void setRecipeSource(String recipeSource) {
        this.recipeSource = recipeSource;
    }

//    public CookingMethod getCookingMethod() {
//        return cookingMethod;
//    }
//
//    public void setCookingMethod(CookingMethod cookingMethod) {
//        this.cookingMethod = cookingMethod;
//    }
//
//    public Cuisine getCuisine() {
//        return cuisine;
//    }
//
//    public void setCuisine(Cuisine cuisine) {
//        this.cuisine = cuisine;
//    }
//
//    public RecipeVariety getVariety() {
//        return variety;
//    }
//
//    public void setVariety(RecipeVariety variety) {
//        this.variety = variety;
//    }

    public Duration getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Duration prepTime) {
        this.prepTime = prepTime;
    }

    public Duration getCookTime() {
        return cookTime;
    }

    public void setCookTime(Duration cookTime) {
        this.cookTime = cookTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getLegacyCookId() {
        return legacyCookId;
    }

    public void setLegacyCookId(Integer legacyCookId) {
        this.legacyCookId = legacyCookId;
    }

    public Set<RecipeIngredientWithDetails> getRecipeIngredients() {
        return recipeIngredientWithDetails;
    }

    public Recipe setRecipeIngredients(Set<RecipeIngredientWithDetails> recipeIngredientWithDetails) {
        this.recipeIngredientWithDetails = recipeIngredientWithDetails;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return isDeleted() == recipe.isDeleted() && Objects.equals(getId(), recipe.getId()) && Objects.equals(getName(), recipe.getName()) && Objects.equals(getPublishedBy(), recipe.getPublishedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPublishedBy(), isDeleted());
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", servings=" + servings +
                ", dateLastModified=" + dateLastModified +
                ", recipeCategories=" + recipeCategories +
                ", recipeIngredientWithDetails=" + recipeIngredientWithDetails +
                ", publishedBy=" + publishedBy +
                ", prepTime=" + prepTime +
                ", cookTime=" + cookTime +
                '}';
    }
}
