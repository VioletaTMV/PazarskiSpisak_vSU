//package com.pazarskispisak.PazarskiSpisak.web;
//
//import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeViewDTO;
//import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Controller
//public class RecipeDeleteController {
//
//    private final RecipeService recipeService;
//
//    @Autowired
//    public RecipeDeleteController(RecipeService recipeService) {
//        this.recipeService = recipeService;
//    }
//
//    @DeleteMapping("/recipe")
//    public String deleteRecipeByID(@RequestParam("id") Long id) {
//
//        this.recipeService.deleteRecipeById(id);
//
//        return "redirect:/";
//    }
//
//
//}
