package com.pazarskispisak.PazarskiSpisak.service;

import com.pazarskispisak.PazarskiSpisak.models.dtos.ItemCategorySupermarketAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;

import java.util.List;
import java.util.Optional;

public interface ItemCategoryService {
    boolean areImported();

    void saveAll(List<ItemCategorySupermarket> itemCategoriesToSaveToDB);

    ItemCategorySupermarket getCurrentItemCategoryBasedOnLegacy(Short legacyArtcategory);

    List<ItemCategorySupermarket> getIngredientCategoriesSortedAlphabetically();

    Optional<ItemCategorySupermarket> findByName(String value);

    List<ItemCategorySupermarket> getNonFoodItemCategoriesOrderedByOrderInShoppingList();

    boolean saveNewItemCategorySupermarket(ItemCategorySupermarketAddDTO itemCategorySupermarketAddDTO);

}
