package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.*;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @ModelAttribute("recipePictureModel")
    public RecipePictureAddDTO initRecipePictureAddModel() {
        return new RecipePictureAddDTO();
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

//        return "redirect:/recipe?id=" + newRecipeId;
        return "redirect:/recipe/add/picture?id=" + newRecipeId;
    }

    @GetMapping("/recipe/add/picture")
    public String recipePictureAdd(@RequestParam("id") Long recipeId,
                                   @ModelAttribute("recipePictureModel") RecipePictureAddDTO recipePictureAddDTO) {


        RecipePictureAddDTO recipePictureAddDTOfromDB = this.recipeService.getRecipePictureAddDTO(recipeId);
        recipePictureAddDTO.setRecipeId(recipePictureAddDTOfromDB.getRecipeId());
        recipePictureAddDTO.setRecipeName(recipePictureAddDTOfromDB.getRecipeName());
        recipePictureAddDTO.setRecipePublishedById(recipePictureAddDTOfromDB.getRecipePublishedById());

        return "recipe-add-2";
    }

    @PatchMapping("/recipe/add/picture")
    public String addPictureToRecipe(@Valid @ModelAttribute("recipePictureModel") RecipePictureAddDTO recipePictureAddDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     HttpServletRequest httpServletRequest){
//                                     @RequestParam("r-picture")
//                                     MultipartFile multipartFile)

        Optional<UserBasicDTO> userBasicDTOOpt = userBasicDTOOpt = this.userService.getBasicUserData(httpServletRequest.getUserPrincipal().getName());
        boolean operationAllowed = this.recipeService.isCurrentUserAllowedToUploadPictureForCurrentRecipe(userBasicDTOOpt.get(), recipePictureAddDTO);
        if (!operationAllowed){
            redirectAttributes.addFlashAttribute("operationNotAllowed", "Операцията не бе позволена. Снимка към рецепта може да качва единствено нейният автор.");
            return "redirect:/recipe/add/picture?id=" + recipePictureAddDTO.getRecipeId();
        }
        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("recipePictureModel", recipePictureAddDTO);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipePictureModel", bindingResult);
            redirectAttributes.addFlashAttribute("invalidFile", "Снимката не бе добавена поради невалиден файлов формат или надхвърлен максимално допустимия размер. Коригирайте файла и опитайте пак.");
            return "redirect:/recipe/add/picture?id=" + recipePictureAddDTO.getRecipeId();
        }
        try {
            String recipeImageName = this.recipeService.uploadPictureToDirAndGetFileName(
                    recipePictureAddDTO.getMultipartFile(),
                    userBasicDTOOpt.get(),
                    recipePictureAddDTO);
            this.recipeService.savePictureNameForRecipeInDB(
                    recipePictureAddDTO.getRecipeId(),
                    userBasicDTOOpt.get().getId(),
                    recipeImageName);
        } catch (IOException e) {
            System.out.println(e.toString());
            redirectAttributes.addFlashAttribute("uploadFailed", "Снимката не бе запазена. Опитайте пак.");
            return "redirect:/recipe/add/picture?id=" + recipePictureAddDTO.getRecipeId();
        }
        return "redirect:/recipe?id=" + recipePictureAddDTO.getRecipeId();
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
