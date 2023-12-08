package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.AdminUserViewDTO;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class AdminUsersController {

    private UserService userService;
    private RecipeCategoryGroupService recipeCategoryGroupService;

    @Autowired
    public AdminUsersController(UserService userService, RecipeCategoryGroupService recipeCategoryGroupService) {
        this.userService = userService;
        this.recipeCategoryGroupService = recipeCategoryGroupService;
    }


    @GetMapping("/admin/users")
    public String adminUsers(){

        return "redirect:/admin/users/view";
    }

    @GetMapping("/admin/users/view")
    public String adminUsersView(Model model){

        List<AdminUserViewDTO> usersViewForAdminDTO = this.userService.getUsersInfoOrderedByRegistrationDateReversedWithAdminsFirst();

        model.addAttribute("usersList", usersViewForAdminDTO);
        model.addAttribute("recipeCategoryGroups", this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes());

        return "admin-users-view";
    }

    @GetMapping("/admin/users/edit")
    public String adminUsersEdit(){

        return "admin-users-edit";
    }


}
