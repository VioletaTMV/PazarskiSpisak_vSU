//package com.pazarskispisak.PazarskiSpisak.web;
//
//import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
//import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//import java.util.List;
//
//@Controller
//public class InterceptorController {
//
//    private RecipeCategoryGroupService recipeCategoryGroupService;
//
//    @Autowired
//    public InterceptorController(RecipeCategoryGroupService recipeCategoryGroupService) {
//        this.recipeCategoryGroupService = recipeCategoryGroupService;
//    }
//
//    @ModelAttribute("recipeCategoryGroups")
//    public List<RecipeCategoryGroupDTO> initActiveRecipeCategoryGroupsList() {
//
//        return this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes();
//    }
//
//    @GetMapping("/intercept")
//    public String showInterceptorMessages() {
//
//        return "interceptedMessages";
//    }
//}
