package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.ItemDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import com.pazarskispisak.PazarskiSpisak.service.ItemService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class AdminProductsController {

    private RecipeCategoryGroupService recipeCategoryGroupService;
    private IngredientService ingredientService;
    private ItemCategoryService itemCategoryService;
    private ItemService itemService;

    @Autowired
    public AdminProductsController(RecipeCategoryGroupService recipeCategoryGroupService, IngredientService ingredientService, ItemCategoryService itemCategoryService, ItemService itemService) {
        this.recipeCategoryGroupService = recipeCategoryGroupService;
        this.ingredientService = ingredientService;
        this.itemCategoryService = itemCategoryService;
        this.itemService = itemService;
    }


    @ModelAttribute("recipeCategoryGroups")
    public List<RecipeCategoryGroupDTO> activeCategoryGroups() {

        return this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes();
    }

    @ModelAttribute("ingredientModel")
    public IngredientDTO initIngredientDTO() {

        IngredientDTO ingredientDTO = new IngredientDTO();
//        ingredientDTO.getIngredientAlternativeMeasurementUnitAndValueListOfDTOs().add(new IngredientAlternativeMeasurementUnitAndValueDTO());

        //ако го правя с Мап горния последен ред е излишно и се заменя с долното
        Map<IngredientMeasurementUnitEnum, Float> prepopulatedMapOfQuantitativeAMU = this.ingredientService.populateIngredientDTOAltUnitsOfMeasureAndValueWithData();
        ingredientDTO.setIngredientAltMUVMap(prepopulatedMapOfQuantitativeAMU);

        return ingredientDTO;
    }

    @ModelAttribute("ingredientCategories")
    public List<ItemCategorySupermarket> initIngredientCategorySupermarket() {
        return this.itemCategoryService.getIngredientCategoriesSortedAlphabetically();
    }

//    @ModelAttribute("alternativeIngredientUnitsOfMeasure")
//    public List<IngredientMeasurementUnitEnum> alternativeMeasuremtnUnitsOnly() {
//        return this.ingredientService.getOnlyQuantifiableAlternativeUnitsOfMeasureForIngredients();
//    }

    @ModelAttribute("limitedMainUnitsOfMeasureForCalcs")
    public List<IngredientMeasurementUnitEnum> initOnlyMainUnitsOfMeasureForIngredients() {
        return this.ingredientService.getOnlyMainUnitsOfMeasureForIngredients();
    }

    @ModelAttribute("nonFoodItemCategories")
    public List<ItemCategorySupermarket> initNonFoodItemCategorySupermarket() {
        return this.itemCategoryService.getNonFoodItemCategoriesOrderedByOrderInShoppingList();
    }

    @ModelAttribute("nonFoodItemModel")
    public ItemDTO initNonFoodItemDTO() {

        return new ItemDTO();
    }

    @GetMapping("/admin")
    public String administer() {

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product")
    public String adminProductsLanding() {

        return "admin-product";
    }

    @GetMapping("/admin/ingredient/add")
    public String administerIngredientAdd() {

        return "admin-ingredient-add";
    }

    @PostMapping("/admin/ingredient/add")
    public String administerIngredientAdd(@Valid @ModelAttribute("ingredientModel") IngredientDTO ingredientDTO,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !this.ingredientService.saveNewIngredient(ingredientDTO)) {

            redirectAttributes.addFlashAttribute("ingredientModel", ingredientDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ingredientModel", bindingResult);

            return "redirect:/admin/ingredient/add";
        }

        return "redirect:/admin/product";
    }

    //долното е ако го правя с лист за бутони за добавяне и премахване
//    @PostMapping(value = "/admin-ingredient-add", params = {"addAlt"})
//    public String addAlt(@ModelAttribute("ingredientModel") IngredientDTO ingredientDTO,
//                         BindingResult bindingResult,
//                         @ModelAttribute("alternativeIngredientUnitsOfMeasure") List<IngredientMeasurementUnitEnum> quantifyableRemainingIngrMUE) {
//
//        if (ingredientDTO.getIngredientAlternativeMeasurementUnitAndValueListOfDTOs().get(ingredientDTO.getIngredientAlternativeMeasurementUnitAndValueListOfDTOs().size()-1).getAltUnitOfMeasure() == null &&
//                ingredientDTO.getIngredientAlternativeMeasurementUnitAndValueListOfDTOs().get(ingredientDTO.getIngredientAlternativeMeasurementUnitAndValueListOfDTOs().size()-1).getValue() == null) {
//            return "/admin-ingredient-add";
//        }
//
//        ingredientDTO.getIngredientAlternativeMeasurementUnitAndValueListOfDTOs().add(new IngredientAlternativeMeasurementUnitAndValueDTO());
//
////в този метод с бутона за добавяне, ако направим redirect бъгва на второто натискане!!! затова направо връщаме страницата
////също така изписването на параметъра в метода ако не е със скобите (за да се покаже че това е точно IngredientDTOто, което е вече в модела) също не работи,
////затова така - цялото това нещо указва че става въпрос за точния ingredientDTO в ingredientModel, който вече сме инициализирали най-горе "@ModelAttribute("ingredientModel") IngredientDTO ingredientDTO"
//        return "/admin-ingredient-add";
//    }
//
//    @PostMapping(value = "/admin-ingredient-add", params = {"rmvAlt"})
//    public String rmvAlt(@ModelAttribute("ingredientModel") IngredientDTO ingredientDTO,
//                         BindingResult bindingResult,
//                         HttpServletRequest httpServletRequest){
//
//        int indexAltUnitToRemove = Integer.parseInt(httpServletRequest.getParameter("rmvAlt"));
//        ingredientDTO.getIngredientAlternativeMeasurementUnitAndValueListOfDTOs().remove(indexAltUnitToRemove);
//
//        System.out.println();
//
//        return "/admin-ingredient-add";
//    }

    //ot tuk do dolniq komentar da iztria


    //ot gornia komentar do tuk da iztriq


    @GetMapping("/admin/ingredient/edit")
    public String administerIngredientEdit(Model model) {

        model.addAttribute("ingredients", this.ingredientService.getAllIngredientsSortedAlphabetically());

//        model.addAttribute("ingredientCategories", this.itemCategoryService.getIngredientCategoriesSortedAlphabetically());
        model.addAttribute("limitedMainUnitsOfMeasureForCalcs", this.ingredientService.getOnlyMainUnitsOfMeasureForIngredients());
        model.addAttribute("alternativeIngredientUnitsOfMeasure", this.ingredientService.getOnlyQuantifiableAlternativeUnitsOfMeasureForIngredients());


        return "admin-ingredient-edit";
    }

    @PostMapping("/admin/ingredient/edit")
    public String administerIngredientEdit(@Valid IngredientDTO ingredientDTO,
                                           BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("ingredientModel", ingredientDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ingredientModel", bindingResult);

            return "redirect:/admin/ingredient/edit";
        }

//update the product
//        redirectAttributes.addFlashAttribute("messageSuccess", "Successfully updated product");

        return "redirect:/admin";
    }

    @GetMapping("/admin/ingredient/delete")
    public String administerProductDelete(Model model) {


        return "admin-ingredient-delete";
    }

    @GetMapping("/admin/item/add")
    public String administerItemAdd(Model model) {


        return "admin-item-add";
    }

    @PostMapping("/admin/item/add")
    public String administerNonFoodItemAdd(@Valid @ModelAttribute("nonFoodItemModel") ItemDTO itemDTO,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !this.itemService.saveNewItem(itemDTO)) {

            redirectAttributes.addFlashAttribute("nonFoodItemModel", itemDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.nonFoodItemModel", bindingResult);

            return "redirect:/admin/item/add";
        }

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/item/edit")
    public String administerItemEdit(Model model) {


        return "admin-item-edit";
    }

    @GetMapping("/admin/item/delete")
    public String administerItemDelete(Model model) {


        return "admin-item-delete";
    }
}
