package com.pazarskispisak.PazarskiSpisak.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {

//    void importUsers() throws FileNotFoundException;
    void importItemCategories() throws FileNotFoundException;
    void importIngredientsAndTheirAltMeasurements() throws FileNotFoundException;
    void importItems() throws FileNotFoundException;
    void importRecipes() throws FileNotFoundException;
    void importRecipeIngredients() throws FileNotFoundException;
    void fillRecipeCategoriesGroup();

    default void seedDatabase() throws IOException {
//        importUsers();
        importItemCategories();
        importIngredientsAndTheirAltMeasurements();
        importItems();
        importRecipes();
        importRecipeIngredients();
        fillRecipeCategoriesGroup();
    }


}
