package com.pazarskispisak.PazarskiSpisak.service;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeShortInfoDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.ShopListProductsDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.ShopListRecipesDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.UserBasicDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.ShoppingListFromRecipes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShoppingListFromRecipesService {

    Optional<ShoppingListFromRecipes> findByCookerId(Long id);

    boolean currentUserHasRecipesInShoppingList(Long id);

    List<RecipeShortInfoDTO> getRecipesShortInfoWithDesiredServingsList(Long id);

    void createNewShopListForUser(UserBasicDTO userBasicDTO);

    void addRecipeToCurrentUserShopList(UserBasicDTO userBasicDTO, Long recipeId);

    boolean updateShoppingListWithRecipesDesiredServingsQtyPossibleChanges(ShopListRecipesDTO shopListRecipesDTO);

    void removeRecipeFromCurrentUserShopList(ShopListRecipesDTO shopListRecipesDTO);

    ShopListProductsDTO getShopListProductsForUser(String userEmail);

    void updateCheckedStatusOfProductsBought(String[] checkboxStatusUpdates, String userEmail, String hideCheckedStatus);

    void findAndDeleteShopListsInactiveByLastAccessedDate(LocalDate inactiveShoplistDate);
}
