package com.pazarskispisak.PazarskiSpisak.models.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
@DiscriminatorValue(value = "item")
public /*abstract*/ class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_type", insertable = false, updatable = false)
    private String itemType;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_category_id", referencedColumnName = "id")
    private ItemCategorySupermarket itemCategorySupermarket;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "legacy_article_id")
    private Short legacyArticleId;

    public Item(){}

    public Item(Long id, String name, ItemCategorySupermarket itemCategorySupermarket, Short legacyArticleId) {
        this.id = id;
        this.name = name;
        this.itemCategorySupermarket = itemCategorySupermarket;
        this.legacyArticleId = legacyArticleId;
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

    public ItemCategorySupermarket getItemCategory() {
        return itemCategorySupermarket;
    }

    public void setItemCategory(ItemCategorySupermarket itemCategorySupermarket) {
        this.itemCategorySupermarket = itemCategorySupermarket;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public Item setDeleted(Boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    public Short getLegacyArticleId() {
        return legacyArticleId;
    }

    public void setLegacyArticleId(Short legacyArticleId) {
        this.legacyArticleId = legacyArticleId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(getId(), item.getId()) && Objects.equals(getName(), item.getName()) && Objects.equals(getItemCategory(), item.getItemCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getItemCategory());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemType='" + itemType + '\'' +
                ", name='" + name + '\'' +
                ", itemCategorySupermarket=" + itemCategorySupermarket +
                ", isDeleted=" + isDeleted +
                ", legacyArticleId=" + legacyArticleId +
                '}';
    }
}

