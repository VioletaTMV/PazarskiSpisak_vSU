package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.models.entities.ShoppingListFromRecipes;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShopListProductsDTO {

   private Long cookerId;
   //TODO: change to Map the below with key = Product Category
//    private List<ProductsInfoForShopListDTO> ingredientsPurchaseStatusList;
    private Map<String, List<ProductsInfoForShopListDTO>> ingredientsPurchaseStatusListByProductCategoryMap;
    private boolean hideChecked;
    private boolean pureAlphabetOrder;

    public ShopListProductsDTO(){
//        this.ingredientsPurchaseStatusList = new ArrayList<>();
        this.ingredientsPurchaseStatusListByProductCategoryMap = new LinkedHashMap<>();
    }

    public Long getCookerId() {
        return cookerId;
    }

    public void setCookerId(Long cookerId) {
        this.cookerId = cookerId;
    }

//    public List<ProductsInfoForShopListDTO> getIngredientsPurchaseStatusList() {
//        return ingredientsPurchaseStatusList;
//    }
//
//    public void setIngredientsPurchaseStatusList(List<ProductsInfoForShopListDTO> ingredientsPurchaseStatusList) {
//        this.ingredientsPurchaseStatusList = ingredientsPurchaseStatusList;
//    }


    public Map<String, List<ProductsInfoForShopListDTO>> getIngredientsPurchaseStatusListByProductCategoryMap() {
        return ingredientsPurchaseStatusListByProductCategoryMap;
    }

    public void setIngredientsPurchaseStatusListByProductCategoryMap(Map<String, List<ProductsInfoForShopListDTO>> ingredientsPurchaseStatusListByProductCategoryMap) {
        this.ingredientsPurchaseStatusListByProductCategoryMap = ingredientsPurchaseStatusListByProductCategoryMap;
    }

    public boolean getHideChecked() {
        return hideChecked;
    }

    public void setHideChecked(boolean hideChecked) {
        this.hideChecked = hideChecked;
    }

    public boolean getPureAlphabetOrder() {
        return pureAlphabetOrder;
    }

    public void setPureAlphabetOrder(boolean pureAlphabetOrder) {
        this.pureAlphabetOrder = pureAlphabetOrder;
    }

//    public void addProductInfoForShopList(ProductsInfoForShopListDTO productsInfoForShopListDTO) {
//
//        this.ingredientsPurchaseStatusList.add(productsInfoForShopListDTO);
//    }

    public void addProductInfoForShopList(ProductsInfoForShopListDTO productsInfoForShopListDTO, String itemCategorySupermarketName){

//        if(!this.ingredientsPurchaseStatusListByProductCategoryMap.containsKey(itemCategorySupermarketName)){
//            this.ingredientsPurchaseStatusListByProductCategoryMap.put(itemCategorySupermarketName, new ArrayList<>());
//        }
        this.ingredientsPurchaseStatusListByProductCategoryMap.get(itemCategorySupermarketName).add(productsInfoForShopListDTO);
    }

    public void putCategoriesToIngredientsPurchaseStatusListByProductCategoryMap(LinkedHashMap<String, List<ProductsInfoForShopListDTO>> productInfoByCategoryMap) {

        this.setIngredientsPurchaseStatusListByProductCategoryMap(productInfoByCategoryMap);
    }

//    public static ShopListProductsDTO orderCategories(){
//
//        this.getIngredientsPurchaseStatusListByProductCategoryMap().entrySet()
//                .stream()
//                .sorted(e -> getCategoryOrderInShopListByCategoryName(e.getKey())
//    }
//
//    private static int getCategoryOrderInShopListByCategoryName(String key) {
//
//
//    }
}
