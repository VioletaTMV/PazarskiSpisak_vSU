package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeViewDTO;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class RecipesByCategoryController {

    private RecipeService recipeService;
    private RecipeCategoryGroupService recipeCategoryGroupService;

    @Autowired
    public RecipesByCategoryController(RecipeService recipeService, RecipeCategoryGroupService recipeCategoryGroupService) {
        this.recipeService = recipeService;
        this.recipeCategoryGroupService = recipeCategoryGroupService;
    }

    @GetMapping("/recipes-by-category")
    public String viewRecipesByCategory(Model model, @RequestParam("id") String id) {

        Set<RecipeViewDTO> recipeViewDTOSetOrderedByDateLastModified = this.recipeService.findAllByCategoryOrderedByDateLastModified(RecipeCategoryEnum.valueOf(id));

        model.addAttribute("recipes", recipeViewDTOSetOrderedByDateLastModified);
        model.addAttribute("recipeCategoryGroups", this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes());

        String categoryBG = RecipeCategoryEnum.valueOf(id).getCategoryBG();
        model.addAttribute("category", categoryBG);
        model.addAttribute("recipeCount", recipeViewDTOSetOrderedByDateLastModified.size());

        return "recipes-by-category";

    }
}
