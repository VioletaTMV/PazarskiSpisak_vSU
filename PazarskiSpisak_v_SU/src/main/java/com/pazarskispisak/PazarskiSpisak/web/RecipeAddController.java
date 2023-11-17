package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeIngredientWithDetailsAddDTO;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RecipeAddController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final RecipeCategoryGroupService recipeCategoryGroupService;
    private UserService userService;

    @Autowired
    public RecipeAddController(RecipeService recipeService, IngredientService ingredientService, RecipeCategoryGroupService recipeCategoryGroupService, UserService userService, UserService userService1) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.recipeCategoryGroupService = recipeCategoryGroupService;
        this.userService = userService1;
    }


    @ModelAttribute("recipeModel")
    public RecipeAddDTO initRecipeAddModel() {

        RecipeAddDTO recipeAddDTO = new RecipeAddDTO();
        recipeAddDTO.getRecipeIngredientWithDetailsAddDTOList().add(new RecipeIngredientWithDetailsAddDTO());

        return recipeAddDTO;
    }

    @ModelAttribute("recipeCategoryGroups")
    public List<RecipeCategoryGroupDTO> initActiveRecipeCategoryGroupsList() {

        return this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes();
    }

    @ModelAttribute("ingredients")
    public List<IngredientDTO> getIngredientDTOList() {

        return this.ingredientService.getAllIngredientsSortedAlphabetically();
    }

    @ModelAttribute("recipeCategoriesWithGrouping")
    public List<RecipeCategoryGroupDTO> getRecipeCategoriesGrouppedByCategoryGroup() {

        return this.recipeCategoryGroupService.findAllOrderByGroupDisplayOrder();
    }


    @GetMapping("/recipe/add")
    public String recipeAdd() {

        return "recipe-add-1";
    }

    @PostMapping("/recipe/add")
    public String addRecipe(@Valid @ModelAttribute("recipeModel") RecipeAddDTO recipeAddDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest httpServletRequest) {


        if (bindingResult.hasErrors()) {

            //edge case, когато юзъра не е избрал мерна единица за продукта и събмитне цялата форма за валидация, ако долния ред го няма и тук, не му излиза падащо
            //меню за вече избран преди събмит на формата продукт
            this.ingredientService.addAllowedMeasurementUnitsForEachProductSelectedToBindToItsOptionsOnReload(recipeAddDTO.getRecipeIngredientWithDetailsAddDTOList());

            redirectAttributes.addFlashAttribute("recipeModel", recipeAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeModel", bindingResult);

            return "redirect:/recipe/add";
        }

        String currentUserEmail = httpServletRequest.getUserPrincipal().getName();

        if (!currentUserIsAuthenticated(currentUserEmail)){
            return "redirect:/user/login";
        }

        Long newRecipeId = this.recipeService.saveWithProducts(recipeAddDTO, currentUserEmail);

        return "redirect:/recipe?id=" + newRecipeId;
    }



    @PostMapping(value = "/recipe/add", params = {"addRecipeIngr"})
    public String addRecipeProduct(@ModelAttribute("recipeModel") RecipeAddDTO recipeAddDTO,
                                   BindingResult bindingResult) {

        this.ingredientService.addAllowedMeasurementUnitsForEachProductSelectedToBindToItsOptionsOnReload(recipeAddDTO.getRecipeIngredientWithDetailsAddDTOList());

        if (!lastProductAddOptionIsSelected(recipeAddDTO.getRecipeIngredientWithDetailsAddDTOList())){

            return "recipe-add-1";
        }

        recipeAddDTO.getRecipeIngredientWithDetailsAddDTOList().add(new RecipeIngredientWithDetailsAddDTO());

        return "recipe-add-1";

    }

    @PostMapping(value = "/recipe/add", params = {"rmvRecipeIngr"})
    public String rmvRecipeProduct(@ModelAttribute("recipeModel") RecipeAddDTO recipeAddDTO,
                         BindingResult bindingResult,
                         HttpServletRequest httpServletRequest){

        int indexOfProductToRemoveFromRecipeAddTemplate = Integer.parseInt(httpServletRequest.getParameter("rmvRecipeIngr"));
        recipeAddDTO.getRecipeIngredientWithDetailsAddDTOList().remove(indexOfProductToRemoveFromRecipeAddTemplate);

        this.ingredientService.addAllowedMeasurementUnitsForEachProductSelectedToBindToItsOptionsOnReload(recipeAddDTO.getRecipeIngredientWithDetailsAddDTOList());

        return "recipe-add-1";
    }



    private boolean lastProductAddOptionIsSelected(List<RecipeIngredientWithDetailsAddDTO> recipeIngredientWithDetailsAddDTOList) {

        return this.ingredientService.ingredientExists(recipeIngredientWithDetailsAddDTOList.get(recipeIngredientWithDetailsAddDTOList.size() - 1).getIngredientId());

    }

    private boolean currentUserIsAuthenticated(String currentUserEmail) {

        return this.userService.findByEmail(currentUserEmail).isPresent();

    }
}
