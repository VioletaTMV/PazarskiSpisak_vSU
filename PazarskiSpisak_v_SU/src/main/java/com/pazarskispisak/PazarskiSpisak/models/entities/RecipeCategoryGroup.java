package com.pazarskispisak.PazarskiSpisak.models.entities;

import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "RecipeCategoryGroups")
public class RecipeCategoryGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name", nullable = false, unique = true)
    private String groupName;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = RecipeCategoryEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_categories_grouping",
            joinColumns = @JoinColumn(name = "recipe_category_group_id"))
    private Set<RecipeCategoryEnum> recipeCategories;

    @Column(name = "group_display_order", unique = true)
    private Integer groupDisplayOrder;

    public RecipeCategoryGroup(){
        this.recipeCategories = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public RecipeCategoryGroup setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Set<RecipeCategoryEnum> getRecipeCategories() {
        return recipeCategories;
    }

    public void setRecipeCategories(Set<RecipeCategoryEnum> recipeCategories) {
        this.recipeCategories = recipeCategories;
    }

    public Integer getGroupDisplayOrder() {
        return groupDisplayOrder;
    }

    public RecipeCategoryGroup setGroupDisplayOrder(Integer groupDisplayOrder) {
        this.groupDisplayOrder = groupDisplayOrder;
        return this;
    }

    public void addRecipeCategory(RecipeCategoryEnum category){
        this.recipeCategories.add(category);
    }
}
