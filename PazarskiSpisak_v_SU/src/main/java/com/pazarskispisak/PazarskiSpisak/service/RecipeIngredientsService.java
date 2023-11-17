package com.pazarskispisak.PazarskiSpisak.service;

import com.pazarskispisak.PazarskiSpisak.models.entities.RecipeIngredientWithDetails;

import java.util.List;

public interface RecipeIngredientsService {
    boolean areImported();

    void saveAll(List<RecipeIngredientWithDetails> recipeIngredientWithDetailsToSaveToDB);
}
