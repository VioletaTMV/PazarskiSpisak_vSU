package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class RecipeCategoryGroupDTO {

    private String groupName;

    @Enumerated(EnumType.STRING)
    private Set<RecipeCategoryEnum> recipeCategories;

    private Integer groupDisplayOrder;

    public RecipeCategoryGroupDTO(){
        this.recipeCategories = new LinkedHashSet<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public RecipeCategoryGroupDTO setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }


    public Set<RecipeCategoryEnum> getRecipeCategories() {
        return recipeCategories;
    }

    public RecipeCategoryGroupDTO setRecipeCategories(Set<RecipeCategoryEnum> recipeCategories) {
        this.recipeCategories = recipeCategories;
        return this;
    }

    public Integer getGroupDisplayOrder() {
        return groupDisplayOrder;
    }

    public RecipeCategoryGroupDTO setGroupDisplayOrder(Integer groupDisplayOrder) {
        this.groupDisplayOrder = groupDisplayOrder;
        return this;
    }
}
