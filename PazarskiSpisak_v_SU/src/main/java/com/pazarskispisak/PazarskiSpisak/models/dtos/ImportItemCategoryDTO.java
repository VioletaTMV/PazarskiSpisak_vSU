package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Column;

public class ImportItemCategoryDTO {

    @Expose
    @SerializedName("artcategory_id")
    private Short legacyArtcategoryId;

    @Expose
    @SerializedName("artcategory_name")
    private String name;

    @Expose
    @SerializedName("artcategory_food")
    private Short isFood;

    @Expose
    @SerializedName("artcategory_order")
    private Short orderInShoppingList;

    public ImportItemCategoryDTO(){}

    public ImportItemCategoryDTO(Short legacyArtcategoryId, String name, Short isFood, Short orderInShoppingList) {
        this.legacyArtcategoryId = legacyArtcategoryId;
        this.name = name;
        this.isFood = isFood;
        this.orderInShoppingList = orderInShoppingList;
    }

    public Short getLegacyArtcategoryId() {
        return legacyArtcategoryId;
    }

    public void setLegacyArtcategoryId(Short legacyArtcategoryId) {
        this.legacyArtcategoryId = legacyArtcategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getIsFood() {
        return isFood;
    }

    public void setIsFood(Short isFood) {
        this.isFood = isFood;
    }

    public Short getOrderInShoppingList() {
        return orderInShoppingList;
    }

    public void setOrderInShoppingList(Short orderInShoppingList) {
        this.orderInShoppingList = orderInShoppingList;
    }
}
