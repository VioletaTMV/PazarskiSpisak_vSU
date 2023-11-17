package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.ItemCategorySupermarketAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminProductCategoriesController {

    private RecipeCategoryGroupService recipeCategoryGroupService;
    private ItemCategoryService itemCategoryService;

    @Autowired
    public AdminProductCategoriesController(RecipeCategoryGroupService recipeCategoryGroupService, ItemCategoryService itemCategoryService) {
        this.recipeCategoryGroupService = recipeCategoryGroupService;
        this.itemCategoryService = itemCategoryService;
    }

    @ModelAttribute("recipeCategoryGroups")
    public List<RecipeCategoryGroupDTO> activeCategoryGroups() {

        return this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes();
    }


    @GetMapping("/admin/product-categories")
    public String adminProductCategories() {

        return "redirect:/admin/product-categories/add";
    }

    @GetMapping("/admin/product-categories/add")
    public String adminProductCategoriesAdd(Model model) {

        if (!model.containsAttribute("addProductCategoryModel")) {
            model.addAttribute("addProductCategoryModel",
                    new ItemCategorySupermarketAddDTO());
        }

        return "admin-product-categories-add";
    }

    @PostMapping("/admin/product-categories/add")
    public String adminProductCategoriesAdd(@Valid ItemCategorySupermarketAddDTO itemCategorySupermarketAddDTO,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !this.itemCategoryService.saveNewItemCategorySupermarket(itemCategorySupermarketAddDTO)) {

            redirectAttributes.addFlashAttribute("addProductCategoryModel", itemCategorySupermarketAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductCategoryModel", bindingResult);

            return "redirect:/admin/product-categories/add";
        }

        return "redirect:/admin/product-categories";
    }

    @GetMapping("/admin/product-categories/edit")
    public String adminProductCategoriesEdit() {

        return "admin-product-categories-edit";
    }

    @GetMapping("/admin/product-categories/delete")
    public String adminProductCategoriesDelete() {

        return "admin-product-categories-delete";
    }

}
