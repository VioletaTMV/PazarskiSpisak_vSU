package com.pazarskispisak.PazarskiSpisak.service;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.RecipeCategoryGroup;

import java.util.List;

public interface RecipeCategoryGroupService {
    boolean isFilled();

    void save(RecipeCategoryGroup categoryGroup);

    List<RecipeCategoryGroupDTO> getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes();

    List<RecipeCategoryGroupDTO> findAllOrderByGroupDisplayOrder();
}
