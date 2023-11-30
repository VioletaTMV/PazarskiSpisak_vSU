package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemCategoryServiceImplTest {

    @Test
    void getIngredientCategoriesSortedAlphabetically() {
    }

    @Test
    void findByName() {
    }

    @Test
    void getNonFoodItemCategoriesOrderedByOrderInShoppingList() {
    }

    @Test
    void saveNewItemCategorySupermarket() {
    }

    public static ItemCategorySupermarket createTestItemCategorySupermarket(){

        ItemCategorySupermarket testFoodItemCategorySupermarket = new ItemCategorySupermarket();
        testFoodItemCategorySupermarket.setName("productCategoryName");
        testFoodItemCategorySupermarket.setFood(true);
        testFoodItemCategorySupermarket.setOrderInShoppingList((short) 1);

        return testFoodItemCategorySupermarket;

    }
}