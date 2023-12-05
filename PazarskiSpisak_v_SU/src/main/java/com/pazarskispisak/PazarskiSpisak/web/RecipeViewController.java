package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeViewDTO;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class RecipeViewController {

    private RecipeService recipeService;
    private RecipeCategoryGroupService recipeCategoryGroupService;

    @Autowired
    public RecipeViewController(RecipeService recipeService, RecipeCategoryGroupService recipeCategoryGroupService) {
        this.recipeService = recipeService;
        this.recipeCategoryGroupService = recipeCategoryGroupService;
    }

    @GetMapping("/recipe")
    public String recipe(Model model, @RequestParam("id") Long id) {

        RecipeViewDTO recipeViewDTO = this.recipeService.getByRecipeId(id);

        model.addAttribute("recipe", recipeViewDTO);
        model.addAttribute("recipeCategoryGroups", this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes());

        return "recipe";
    }




    @DeleteMapping("/recipe")
    public String deleteRecipeByID(@RequestParam("id") Long id,
                                   Principal principal) {

        this.recipeService.deleteRecipeById(id, principal.getName());

        return "redirect:/";
    }

}
