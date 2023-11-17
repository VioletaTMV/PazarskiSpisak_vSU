package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.ShoppingListFromRecipesService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;

@Controller
public class ShopListController {

    private ShoppingListFromRecipesService shoppingListFromRecipesService;
    private RecipeCategoryGroupService recipeCategoryGroupService;
    private UserService userService;

    @Autowired
    public ShopListController(ShoppingListFromRecipesService shoppingListFromRecipesService, RecipeCategoryGroupService recipeCategoryGroupService, UserService userService) {
        this.shoppingListFromRecipesService = shoppingListFromRecipesService;
        this.recipeCategoryGroupService = recipeCategoryGroupService;
        this.userService = userService;
    }

//    @ModelAttribute("shopListModel")
//    public ShopListRecipesDTO initUserShopListDTO() {
//
//        ShopListRecipesDTO shopListRecipesDTO = new ShopListRecipesDTO();
//
//        return shopListRecipesDTO;
//    }

    @ModelAttribute("shopListRecipesModel")
    public ShopListRecipesDTO initUserShopListRecipesModel() {

        ShopListRecipesDTO shopListRecipesDTO = new ShopListRecipesDTO();

        return shopListRecipesDTO;
    }

    @ModelAttribute("recipeCategoryGroups")
    public List<RecipeCategoryGroupDTO> initRecipeCategoryGroupModel() {

        List<RecipeCategoryGroupDTO> rcgModel = this.recipeCategoryGroupService.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes();

        return rcgModel;
    }

    @ModelAttribute("shopListProductsModel")
    public ShopListProductsDTO initShopListProductsModel(Principal principal){

        Optional<UserBasicDTO> userBasicDTOOpt = userBasicDTOOpt = this.userService.getBasicUserData(principal.getName());

        ShopListProductsDTO shopListProductsDTO = new ShopListProductsDTO();
        shopListProductsDTO.setCookerId(userBasicDTOOpt.get().getId());

        ShopListProductsDTO shopListProductsRetrievedFromEntity = this.shoppingListFromRecipesService.getShopListProductsForUser(principal.getName());

        shopListProductsDTO.setIngredientsPurchaseStatusListByProductCategoryMap(shopListProductsRetrievedFromEntity.getIngredientsPurchaseStatusListByProductCategoryMap());
        shopListProductsDTO.setHideChecked(shopListProductsRetrievedFromEntity.getHideChecked());
        shopListProductsDTO.setPureAlphabetOrder(shopListProductsRetrievedFromEntity.getPureAlphabetOrder());

        return shopListProductsDTO;
    }


    @GetMapping("/list/recipes")
    public String showListOfChosenRecipes(@ModelAttribute("shopListRecipesModel") ShopListRecipesDTO shopListRecipesDTO,
                                          Model model,
                                          Principal principal) {

        if (principal == null) {
            model.addAttribute("userNotRegisteredMessage", "Списъка е празен. Започни да добавяш рецепти. Необходимо е да си регистриран потребител за тази функционалност.");
            return "my-list-recipes";
        }

        Optional<UserBasicDTO> userBasicDTOOpt = userBasicDTOOpt = this.userService.getBasicUserData(principal.getName());
        shopListRecipesDTO.setUserBasicDTO(userBasicDTOOpt.get());

        boolean currentUserHasShopList = this.shoppingListFromRecipesService.currentUserHasRecipesInShoppingList(userBasicDTOOpt.get().getId());

        if (currentUserHasShopList) {

            List<RecipeShortInfoDTO> recipesShortInfoWithDesiredServingsList = this.shoppingListFromRecipesService.getRecipesShortInfoWithDesiredServingsList(userBasicDTOOpt.get().getId());

            shopListRecipesDTO.setShopListRecipes(recipesShortInfoWithDesiredServingsList);

        } else {
            model.addAttribute("emptyShopListMessage", "Списъка е празен. Започни да добавяш рецепти.");
        }

        return "my-list-recipes";
    }

    @PutMapping("/list/recipes")
    public String updateShopList(@Valid @ModelAttribute("shopListRecipesModel") ShopListRecipesDTO shopListRecipesDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {

        Optional<UserBasicDTO> userBasicDTOOpt = userBasicDTOOpt = this.userService.getBasicUserData(principal.getName());
        shopListRecipesDTO.setUserBasicDTO(userBasicDTOOpt.get());

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("shopListRecipesModel", shopListRecipesDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.shopListRecipesModel", bindingResult);

            return "redirect:/list/recipes";
        }

        boolean updateSucceeded = this.shoppingListFromRecipesService.updateShoppingListWithRecipesDesiredServingsQtyPossibleChanges(shopListRecipesDTO);

        if (!updateSucceeded) {
            return "redirect:/user/login";
        }

        return "redirect:/list/products";
    }

    @PostMapping(value = "/list/add-recipe", params = {"addRecipeToShopList"})
    public String addToShopList(@RequestParam("id") Long id,
                                @ModelAttribute("shopListRecipesModel") ShopListRecipesDTO shopListRecipesDTO,
                                Principal principal) {

        if (principal == null) {
            return "redirect:/user/login";
        }

        Optional<UserBasicDTO> userBasicDTOOpt = userBasicDTOOpt = this.userService.getBasicUserData(principal.getName());

        Long recipeId = id;

        boolean currentUserHasShopList = this.shoppingListFromRecipesService.currentUserHasRecipesInShoppingList(userBasicDTOOpt.get().getId());

        if (!currentUserHasShopList) {
            //create new shopping list for user
            this.shoppingListFromRecipesService.createNewShopListForUser(userBasicDTOOpt.get());

        }
        //add recipe to user shopping list
        this.shoppingListFromRecipesService.addRecipeToCurrentUserShopList(userBasicDTOOpt.get(), recipeId);

        return "redirect:/recipe?id=" + id;

    }

    @PostMapping(value = "/list/add-recipe", params = {"addRecipeToShopListFromRecipeCategoriesList"})
    public String addToShopList(@RequestParam("id") String id,
                                @ModelAttribute("shopListRecipesModel") ShopListRecipesDTO shopListRecipesDTO,
                                Principal principal,
                                HttpServletRequest httpServletRequest) {

        if (principal == null) {
            return "redirect:/user/login";
        }

        Optional<UserBasicDTO> userBasicDTOOpt = userBasicDTOOpt = this.userService.getBasicUserData(principal.getName());

        String categoryBG = id;
        RecipeCategoryEnum recipeCategoryEnumName = Arrays.stream(RecipeCategoryEnum.values())
                .filter(rce -> rce.getCategoryBG().equals(categoryBG))
                .findFirst()
                .get();

        Long recipeId = Long.parseLong(httpServletRequest.getParameter("addRecipeToShopListFromRecipeCategoriesList"));


        boolean currentUserHasShopList = this.shoppingListFromRecipesService.currentUserHasRecipesInShoppingList(userBasicDTOOpt.get().getId());

        if (!currentUserHasShopList) {
            //create new shopping list for user
            this.shoppingListFromRecipesService.createNewShopListForUser(userBasicDTOOpt.get());

        }
        //add recipe to user shopping list
        this.shoppingListFromRecipesService.addRecipeToCurrentUserShopList(userBasicDTOOpt.get(), recipeId);

        return "redirect:/recipes-by-category?id=" + recipeCategoryEnumName;

    }

    @PutMapping(value = "/list/recipes", params = {"rmvRecipeFromShopList"})
    public String rmvRecipeFromShopList(@ModelAttribute("shopListRecipesModel") ShopListRecipesDTO shopListRecipesDTO,
                                        BindingResult bindingResult,
                                        HttpServletRequest httpServletRequest) {

        int indexOfRecipeShortInfoToRemoveFromShopListTemplate = Integer.parseInt(httpServletRequest.getParameter("rmvRecipeFromShopList"));
        shopListRecipesDTO.getShopListRecipes().remove(indexOfRecipeShortInfoToRemoveFromShopListTemplate);

        if (httpServletRequest.getUserPrincipal() == null) {
            return "redirect:/user/login";
        }

        Optional<UserBasicDTO> userBasicDTOOpt = userBasicDTOOpt = this.userService.getBasicUserData(httpServletRequest.getUserPrincipal().getName());
        shopListRecipesDTO.setUserBasicDTO(userBasicDTOOpt.get());

        //remove recipe from user shopping list
        this.shoppingListFromRecipesService.removeRecipeFromCurrentUserShopList(shopListRecipesDTO);

        return "redirect:/list/recipes";

    }


    @GetMapping("/list/products")
    public String showListOfProducts(@ModelAttribute("shopListProductsModel") ShopListProductsDTO shopListProductsDTO,
                                     Model model,
                                     Principal principal) {
        //TODO: да преместя ModelAttribute да не се инициализира най-отгоре за ShopListProductsDTO тъй като е релевантен само за този метод

//        Optional<UserBasicDTO> userBasicDTOOpt = userBasicDTOOpt = this.userService.getBasicUserData(principal.getName());
//        shopListProductsDTO.setCookerId(userBasicDTOOpt.get().getId());
//
//       ShopListProductsDTO shopListProductsRetrievedFromEntity = this.shoppingListFromRecipesService.getShopListProductsForUser(principal.getName());
//
//       shopListProductsDTO.setIngredientsPurchaseStatusListByProductCategoryMap(shopListProductsRetrievedFromEntity.getIngredientsPurchaseStatusListByProductCategoryMap());
//       shopListProductsDTO.setHideChecked(shopListProductsRetrievedFromEntity.getHideChecked());
//       shopListProductsDTO.setPureAlphabetOrder(shopListProductsRetrievedFromEntity.getPureAlphabetOrder());

        return "my-list-products";
    }

    @PutMapping(value = "/list/products",  params = {"checkboxStatusUpdate"})
    public String updatedCheckedStatusOfItemsInShopList (Principal principal,
                                              HttpServletRequest httpServletRequest){

        System.out.println();
        String[] checkboxStatusUpdates = httpServletRequest.getParameterMap().get("checkboxStatusUpdate");
        String hideCheckedSlider = httpServletRequest.getParameter("hideCheckedSlider");

        this.shoppingListFromRecipesService.updateCheckedStatusOfProductsBought(checkboxStatusUpdates, principal.getName());

        return "redirect:/list/products";
    }

    @PutMapping(value = "/list/products",  params = {"returnToRecipeList", "checkboxStatusUpdate"})
    public String updatedCheckedStatusOfItemsInShopListAndReturnToRecipesList (Principal principal,
                                                         HttpServletRequest httpServletRequest){

        System.out.println();

        String[] checkboxStatusUpdates = httpServletRequest.getParameterMap().get("checkboxStatusUpdate");

        if (checkboxStatusUpdates.length == 0){
            checkboxStatusUpdates[0]= "dummyEntryToProduceLength1";
        }

        this.shoppingListFromRecipesService.updateCheckedStatusOfProductsBought(checkboxStatusUpdates, principal.getName());


        return "redirect:/list/recipes";
    }
}
