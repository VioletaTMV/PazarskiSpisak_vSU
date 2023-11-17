package com.pazarskispisak.PazarskiSpisak.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminRecipeCategorizationController {

    @GetMapping("/admin-recipe-categorization")
    public String adminRecipeCategorization(){

        return "redirect:/admin-recipe-categorization-view";
    }

    @GetMapping("/admin-recipe-categorization-view")
    public String adminRecipeCategorizationView(){

        return "/admin-recipe-categorization-view";
    }

    @GetMapping("/admin-recipe-categorization-edit")
    public String adminRecipeCategorizationEdit(){

        return "/admin-recipe-categorization-edit";
    }
}
