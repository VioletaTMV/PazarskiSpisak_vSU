package com.pazarskispisak.PazarskiSpisak.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminRecipesController {

    @GetMapping("/admin-recipes")
    public String adminRecipes(){

        return "redirect:/admin-recipes-view";
    }

    @GetMapping("/admin-recipes-view")
    public String adminRecipesView(){

        return "/admin-recipes-view";
    }

    @GetMapping("/admin-recipes-delete")
    public String adminRecipesDelete(){

        return "/admin-recipes-delete";
    }
}
