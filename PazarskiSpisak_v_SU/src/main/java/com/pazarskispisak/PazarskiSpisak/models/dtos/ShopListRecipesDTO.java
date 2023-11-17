package com.pazarskispisak.PazarskiSpisak.models.dtos;

import java.util.*;

public class ShopListRecipesDTO {

    private UserBasicDTO userBasicDTO;
    private List<RecipeShortInfoDTO> shopListRecipes;

    public ShopListRecipesDTO() {
        this.shopListRecipes = new ArrayList<>();
    }

    public UserBasicDTO getUserBasicDTO() {
        return userBasicDTO;
    }

    public ShopListRecipesDTO setUserBasicDTO(UserBasicDTO userBasicDTO) {
        this.userBasicDTO = userBasicDTO;
        return this;
    }

    public List<RecipeShortInfoDTO> getShopListRecipes() {
        return shopListRecipes;
    }

    public ShopListRecipesDTO setShopListRecipes(List<RecipeShortInfoDTO> shopListRecipes) {
        this.shopListRecipes = shopListRecipes;
        return this;
    }

    @Override
    public String toString() {
        return "ShopListRecipesDTO{" +
                "userBasicDTO=" + userBasicDTO +
                ", shopListRecipes=" + shopListRecipes +
                '}';
    }
}
