package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.RecipeCategoryGroup;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.repository.RecipeCategoryGroupRepository;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeCategoryGroupServiceImpl implements RecipeCategoryGroupService {

    private final RecipeCategoryGroupRepository recipeCategoryGroupRepository;
    private ModelMapper modelMapper;
    private RecipeService recipeService;

    @Autowired
    public RecipeCategoryGroupServiceImpl(RecipeCategoryGroupRepository recipeCategoryGroupRepository, ModelMapper modelMapper, RecipeService recipeService) {
        this.recipeCategoryGroupRepository = recipeCategoryGroupRepository;
        this.modelMapper = modelMapper;
        this.recipeService = recipeService;
    }


    @Override
    public boolean isFilled() {
        return this.recipeCategoryGroupRepository.count() > 0;
    }

    @Override
    public void save(RecipeCategoryGroup categoryGroup) {
        this.recipeCategoryGroupRepository.save(categoryGroup);
    }

    @Override
    public List<RecipeCategoryGroupDTO> getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes() {

        List<RecipeCategoryGroup> allRecipeCategoryGroups = this.recipeCategoryGroupRepository.findAll();

        List<RecipeCategoryGroupDTO> recipeCategoryGroupDTOSOrderedByDisplayOrderAndRecCatEnumOrdinals = Arrays.stream(this.modelMapper.map(allRecipeCategoryGroups, RecipeCategoryGroupDTO[].class))
                .sorted(Comparator.comparing(RecipeCategoryGroupDTO::getGroupDisplayOrder))
                .filter(cg -> {
                    Set<RecipeCategoryEnum> activeRecipeCategories = cg.getRecipeCategories().stream()
                            .filter(rc -> this.recipeService.existsByRecipeCategory(rc))
                            .sorted(Comparator.comparing(RecipeCategoryEnum::ordinal))
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                    cg.setRecipeCategories(activeRecipeCategories);

                    return cg.getRecipeCategories().size() > 0;
                })
                .toList();

        return recipeCategoryGroupDTOSOrderedByDisplayOrderAndRecCatEnumOrdinals;
    }

    @Override
    public List<RecipeCategoryGroupDTO> findAllOrderByGroupDisplayOrder() {

        List<RecipeCategoryGroup> allRecipeCategoryGroups = this.recipeCategoryGroupRepository.findByOrderByGroupDisplayOrderAsc();

        RecipeCategoryGroupDTO[] rcgDTOArr = this.modelMapper.map(allRecipeCategoryGroups, RecipeCategoryGroupDTO[].class);

        return Arrays.stream(rcgDTOArr).toList();
    }
}
