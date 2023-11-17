package com.pazarskispisak.PazarskiSpisak.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "items_categories")
public class ItemCategorySupermarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_food", nullable = false)
    private Boolean isFood;

    @Column(name = "order_in_shopping_list")
    private Short orderInShoppingList;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "legacy_article_category_id")
    private Short legacyArtcategoryId;

    public ItemCategorySupermarket(){
        this.isDeleted = false;
    }

    public ItemCategorySupermarket(String name, Boolean isFood, Short orderInShoppingList) {
        this.name = name;
        this.isFood = isFood;
        this.orderInShoppingList = orderInShoppingList;
        this.isDeleted= false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFood() {
        return isFood;
    }

    public void setFood(Boolean food) {
        isFood = food;
    }

    public Short getOrderInShoppingList() {
        return orderInShoppingList;
    }

    public void setOrderInShoppingList(Short orderInShoppingList) {
        this.orderInShoppingList = orderInShoppingList;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public ItemCategorySupermarket setDeleted(Boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    public Short getLegacyArtcategoryId() {
        return legacyArtcategoryId;
    }

    public void setLegacyArtcategoryId(Short legacyArtcategoryId) {
        this.legacyArtcategoryId = legacyArtcategoryId;
    }
}
