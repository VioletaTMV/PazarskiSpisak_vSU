package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.ShopListProductsDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.UserBasicDTO;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.ShoppingListFromRecipesService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ShopListProductsController {

    private ShoppingListFromRecipesService shoppingListFromRecipesService;
    private RecipeCategoryGroupService recipeCategoryGroupService;
    private UserService userService;

    @Autowired
    public ShopListProductsController(ShoppingListFromRecipesService shoppingListFromRecipesService, RecipeCategoryGroupService recipeCategoryGroupService, UserService userService) {
        this.shoppingListFromRecipesService = shoppingListFromRecipesService;
        this.recipeCategoryGroupService = recipeCategoryGroupService;
        this.userService = userService;
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

    @GetMapping("/list/products")
    public String showListOfProducts(@ModelAttribute("shopListProductsModel") ShopListProductsDTO shopListProductsDTO,
                                     Model model,
                                     Principal principal) {


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
