package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserLoginController {

    private final RecipeCategoryGroupService recipeCategoryGroupService;

    @Autowired
    public UserLoginController(RecipeCategoryGroupService recipeCategoryGroupService) {
        this.recipeCategoryGroupService = recipeCategoryGroupService;
    }

    //какво правим с този моделатрибут след като махнахме UserLoginDTOто? След като нямаме Пост мапинг явно няма да ни трябва?
//    @ModelAttribute("userLoginModel")
//    public UserLoginDTO initUserLoginModel() {
//        return new UserLoginDTO();
//    }

    @ModelAttribute("recipeCategoryGroups")
    public List<RecipeCategoryGroupDTO> recipeCategoryGroupDTOList(){

        List<RecipeCategoryGroupDTO> recipeCategoryGroupDTOList = this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes();

        return recipeCategoryGroupDTOList;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        Model model) {

//        model.addAttribute("recipeCategoryGroups", this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes());

        return "login";
    }

    @PostMapping("/login-error")
    public String onFailure(@ModelAttribute("email") String email, Model model){

        model.addAttribute("email", email);
        model.addAttribute("bad_credentials", "true");

        return "login";
    }
}
