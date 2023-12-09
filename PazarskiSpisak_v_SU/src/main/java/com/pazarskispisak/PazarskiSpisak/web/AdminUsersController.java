package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.AdminUserViewDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.UserRolesService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.util.List;

@Controller
public class AdminUsersController {

    private UserService userService;
    private RecipeCategoryGroupService recipeCategoryGroupService;
    private UserRolesService userRolesService;

    @Autowired
    public AdminUsersController(UserService userService, RecipeCategoryGroupService recipeCategoryGroupService, UserRolesService userRolesService) {
        this.userService = userService;
        this.recipeCategoryGroupService = recipeCategoryGroupService;
        this.userRolesService = userRolesService;
    }

    @ModelAttribute("recipeCategoryGroups")
    public List<RecipeCategoryGroupDTO> activeCategoryGroups() {

        return this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes();
    }

    @ModelAttribute("userRolesDTOList")
    public List<String> getUserRolesDTOList() {

        return this.userRolesService.getAllUserRolesDTOList();
    }

    @ModelAttribute("usersList")
    public List<AdminUserViewDTO> initUsersList() {

        return this.userService.getUsersInfoOrderedByRegistrationDateReversedWithAdminsFirst();
    }

    @ModelAttribute("userToUpdate")
    public AdminUserViewDTO initUserToUpdate() {

        return new AdminUserViewDTO();
    }

    @GetMapping("/admin/users")
    public String adminUsers() {

        return "redirect:/admin/users/view";
    }

    @GetMapping("/admin/users/view")
    public String adminUsersView(Model model) {

        return "admin-users-view";
    }

    @GetMapping(value = "/admin/users/edit")
    public String showAdminUserEditForm(@RequestParam("id") Long userId,
                                        @ModelAttribute("userToUpdate") AdminUserViewDTO adminUserViewDTO) {

        AdminUserViewDTO userToUpdateDTO = this.userService.getUserToUpdate(userId);

        adminUserViewDTO.setId(userToUpdateDTO.getId());
        adminUserViewDTO.setEmail(userToUpdateDTO.getEmail());
        adminUserViewDTO.setDisplayNickname(userToUpdateDTO.getDisplayNickname());
        adminUserViewDTO.setDateRegistered(userToUpdateDTO.getDateRegistered());
        adminUserViewDTO.setUserRoles(userToUpdateDTO.getUserRoles());

        return "admin-users-edit";
    }

    @PatchMapping(value = "/admin/users/edit")
    public String updateUserRoles(@RequestParam("id") Long userId,
                                  @Valid AdminUserViewDTO adminUserViewDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userToUpdate", adminUserViewDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userToUpdate", bindingResult);

            return "redirect:/admin/users/edit?id=" + adminUserViewDTO.getId();
        }

        this.userService.updateUserRoles(adminUserViewDTO);

        return "redirect:/admin/users/view";
    }


}
