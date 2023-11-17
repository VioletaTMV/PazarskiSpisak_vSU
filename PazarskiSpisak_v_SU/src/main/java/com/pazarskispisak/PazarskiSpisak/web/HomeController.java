package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController {

    private final RecipeCategoryGroupService recipeCategoryGroupService;

    @Autowired
    public HomeController(RecipeCategoryGroupService recipeCategoryGroupService) {
        this.recipeCategoryGroupService = recipeCategoryGroupService;
    }


    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("recipeCategoryGroups", this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes());

        return "index";
    }

}
