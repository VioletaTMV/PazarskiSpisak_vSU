package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.UserRegisterDTO;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserRegistrationController {

    private final UserService userService;
    private RecipeCategoryGroupService recipeCategoryGroupService;

    @Autowired
    public UserRegistrationController(UserService userService, RecipeCategoryGroupService recipeCategoryGroupService) {
        this.userService = userService;
        this.recipeCategoryGroupService = recipeCategoryGroupService;
    }

//    @ModelAttribute("userModel")
//    public void initUserModel(Model model){
//
//        model.addAttribute("userModel", new UserRegisterDTO());
//    }

    @ModelAttribute("userModel")
    public UserRegisterDTO initUserModel(){
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String register(Model model){

        model.addAttribute("recipeCategoryGroups", this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes());

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);

            return "redirect:/user/register";
        }

        this.userService.register(userModel);

        return "redirect:/login";
    }

}
