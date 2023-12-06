package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.*;
import com.pazarskispisak.PazarskiSpisak.models.entities.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.repository.ShoppingListFromRecipesRepository;
import com.pazarskispisak.PazarskiSpisak.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingListFromRecipesServiceImpl implements ShoppingListFromRecipesService {

    private final ShoppingListFromRecipesRepository shoppingListFromRecipesRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final RecipeService recipeService;
    private final ItemCategoryService itemCategoryService;


    @Autowired
    public ShoppingListFromRecipesServiceImpl(ShoppingListFromRecipesRepository shoppingListFromRecipesRepository, ModelMapper modelMapper, UserService userService, RecipeService recipeService, ItemCategoryService itemCategoryService) {
        this.shoppingListFromRecipesRepository = shoppingListFromRecipesRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.recipeService = recipeService;
        this.itemCategoryService = itemCategoryService;
    }

    public Optional<ShoppingListFromRecipes> findByCookerId(Long id) {

        Optional<ShoppingListFromRecipes> byCookerIdOpt = this.shoppingListFromRecipesRepository.findByCookerId(id);

        return byCookerIdOpt;
    }

    @Override
    public boolean currentUserHasRecipesInShoppingList(Long id) {

        Optional<ShoppingListFromRecipes> shoppingListOpt = findByCookerId(id);

        if (shoppingListOpt.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public List<RecipeShortInfoDTO> getRecipesShortInfoWithDesiredServingsList(Long id) {

        ShoppingListFromRecipes shopListRecipes = findByCookerId(id).get();

        List<RecipeShortInfoDTO> recipeShortInfoDTOList = new ArrayList<>();
        for (Map.Entry<Recipe, Short> recipeEntry : shopListRecipes.getRecipesSelectedWithDesiredServingsMap().entrySet()) {

            RecipeShortInfoDTO recipeShortInfoDTO = this.modelMapper.map(recipeEntry.getKey(), RecipeShortInfoDTO.class);
            Short desiredServingsQty = recipeEntry.getValue();
            recipeShortInfoDTO.setDesiredServings(desiredServingsQty);

            recipeShortInfoDTO.setPicture(this.recipeService.getRecipeImgPath(recipeEntry.getKey().getPicture()));

            recipeShortInfoDTOList.add(recipeShortInfoDTO);
        }

        return recipeShortInfoDTOList;
    }

    @Override
    @Transactional
    public void createNewShopListForUser(UserBasicDTO userBasicDTO) {

        ShoppingListFromRecipes shoppingListFromRecipesToSaveToDB = new ShoppingListFromRecipes();
        User currentUser = this.userService.findById(userBasicDTO.getId()).get();

        shoppingListFromRecipesToSaveToDB.setCooker(currentUser);

        ShoppingListFromRecipes savedShopListWithId = this.shoppingListFromRecipesRepository.save(shoppingListFromRecipesToSaveToDB);

        currentUser.setShoppingListFromRecipes(savedShopListWithId);
        this.userService.save(currentUser);

    }

    @Override
    @Transactional
    public void addRecipeToCurrentUserShopList(UserBasicDTO userBasicDTO, Long recipeId) {

        ShoppingListFromRecipes userShopListFromRecipes = this.shoppingListFromRecipesRepository.findByCookerId(userBasicDTO.getId()).get();

        Recipe recipeToAddToShopList = this.recipeService.findById(recipeId).get();

        userShopListFromRecipes.getRecipesSelectedWithDesiredServingsMap()
                .putIfAbsent(recipeToAddToShopList, recipeToAddToShopList.getServings());

        userShopListFromRecipes = addIngredientsFromAddedRecipesInNeededIngredientsPurchaseStatusMap(userShopListFromRecipes);
        userShopListFromRecipes.setLastAccessedDate(LocalDate.now());

        this.shoppingListFromRecipesRepository.save(userShopListFromRecipes);

    }

    @Override
    @Transactional
    public boolean updateShoppingListWithRecipesDesiredServingsQtyPossibleChanges(ShopListRecipesDTO shopListRecipesDTO) {

        Optional<User> userOpt = this.userService.findById(shopListRecipesDTO.getUserBasicDTO().getId());

        if (userOpt.isEmpty()) {
            return false;
        }

        ShoppingListFromRecipes shoppingListFromRecipes = this.shoppingListFromRecipesRepository.findByCookerId(userOpt.get().getId()).get();

        for (Map.Entry<Recipe, Short> recipeEntry : shoppingListFromRecipes.getRecipesSelectedWithDesiredServingsMap().entrySet()) {

            Long idOfRecipeToUpdate = recipeEntry.getKey().getId();
            Short currentDesiredServingsQty = recipeEntry.getValue();

            for (RecipeShortInfoDTO recipeShortInfo : shopListRecipesDTO.getShopListRecipes()) {

                Long idOfRecipeShortInfo = recipeShortInfo.getId();
                Short newDesiredServingsQty = recipeShortInfo.getDesiredServings();

                if (idOfRecipeToUpdate.equals(idOfRecipeShortInfo) && !currentDesiredServingsQty.equals(newDesiredServingsQty)) {

                    recipeEntry.setValue(newDesiredServingsQty);
                }
            }

        }

        shoppingListFromRecipes.setLastAccessedDate(LocalDate.now());

        this.shoppingListFromRecipesRepository.save(shoppingListFromRecipes);

        return true;
    }

    @Override
    @Transactional
    public void removeRecipeFromCurrentUserShopList(ShopListRecipesDTO shopListRecipesDTO) {

        Optional<User> userOpt = this.userService.findById(shopListRecipesDTO.getUserBasicDTO().getId());

        ShoppingListFromRecipes shoppingListFromRecipes = this.shoppingListFromRecipesRepository.findByCookerId(userOpt.get().getId()).get();

        Map<Recipe, Short> afterRemovalOfDeletedRecipesByUser = shoppingListFromRecipes.getRecipesSelectedWithDesiredServingsMap().entrySet()
                .stream()
                .filter(re -> shopListRecipesDTO.getShopListRecipes()
                        .stream()
                        .map(RecipeShortInfoDTO::getId)
                        .toList()
                        .contains(re.getKey().getId()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        shoppingListFromRecipes.setRecipesSelectedWithDesiredServingsMap(afterRemovalOfDeletedRecipesByUser);
        shoppingListFromRecipes.setLastAccessedDate(LocalDate.now());

        if (shoppingListFromRecipes.getRecipesSelectedWithDesiredServingsMap().size() == 0) {
            //изтрий тотално списъка щом е празен
            userOpt.get().setShoppingListFromRecipes(null);
            this.userService.save(userOpt.get());

            shoppingListFromRecipesRepository.delete(shoppingListFromRecipes);
        } else {
            //ако пък е останала поне една рецепта изтрий излишните продукти от премахнатите рецепти
            removeIngredientsAfterRemovingRecipesInNeededIngredientsPurchaseStatusMap(shoppingListFromRecipes);
            this.shoppingListFromRecipesRepository.save(shoppingListFromRecipes);
        }

    }

    @Override
    @Transactional
    public ShopListProductsDTO getShopListProductsForUser(String userEmail) {

        Optional<ShoppingListFromRecipes> shopListOpt = this.shoppingListFromRecipesRepository.findByCookerEmail(userEmail);

        LinkedHashMap<String, List<ProductsInfoForShopListDTO>> productInfoByCategoryMap = shopListOpt.get().getIngredientsPurchaseStatusMap().keySet().stream()
                .map(i -> i.getItemCategory())
                .sorted((cat1, cat2) -> cat1.getOrderInShoppingList().compareTo(cat2.getOrderInShoppingList()))
                .map(cat -> cat.getName())
                .collect(Collectors.toMap(
                        categoryName -> categoryName,
                        categoryName -> new ArrayList<ProductsInfoForShopListDTO>(),
                        (x, y) -> y,
                        LinkedHashMap::new));

        ShopListProductsDTO shopListProductsDTO = this.modelMapper.map(shopListOpt.get(), ShopListProductsDTO.class);
        shopListProductsDTO.putCategoriesToIngredientsPurchaseStatusListByProductCategoryMap(productInfoByCategoryMap);

        String pattern = "###,###.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        for (Map.Entry<Ingredient, Boolean> ingredientBooleanEntry : shopListOpt.get().getIngredientsPurchaseStatusMap().entrySet()) {

            ProductsInfoForShopListDTO productsInfoForShopListDTO = new ProductsInfoForShopListDTO();
            productsInfoForShopListDTO.setIngredientId(ingredientBooleanEntry.getKey().getId());
            productsInfoForShopListDTO.setIngredientName(ingredientBooleanEntry.getKey().getName());
            productsInfoForShopListDTO.setMeasurementUnitForShopList(ingredientBooleanEntry.getKey().getShoppingListDisplayUnitOfMeasurement().getMeasureBG());
            productsInfoForShopListDTO.setTotalQty(decimalFormat.format(calculateTotalQtyOfProductNeeded(ingredientBooleanEntry.getKey(), shopListOpt.get().getRecipesSelectedWithDesiredServingsMap())));
            productsInfoForShopListDTO.setChecked(ingredientBooleanEntry.getValue());

            String itemCategorySupermarketNameOfCurrentIngredient = ingredientBooleanEntry.getKey().getItemCategory().getName();
            shopListProductsDTO.addProductInfoForShopList(productsInfoForShopListDTO, itemCategorySupermarketNameOfCurrentIngredient);
        }

        shopListProductsDTO.getIngredientsPurchaseStatusListByProductCategoryMap().entrySet()
                .forEach(e -> {
                    List<ProductsInfoForShopListDTO> sorted = e.getValue().stream()
                            .sorted((i1, i2) -> i1.getIngredientName().compareTo(i2.getIngredientName()))
                            .toList();
                    e.setValue(sorted);
                });

        shopListOpt.get().setLastAccessedDate(LocalDate.now());
        this.shoppingListFromRecipesRepository.save(shopListOpt.get());

        return shopListProductsDTO;
    }

    @Override
    @Transactional
    public void updateCheckedStatusOfProductsBought(String[] checkboxStatusUpdates, String userEmail, String hideCheckedStatus) {

        Optional<ShoppingListFromRecipes> shopListOpt = this.shoppingListFromRecipesRepository.findByCookerEmail(userEmail);
        System.out.println();
        for (Map.Entry<Ingredient, Boolean> ingredientBooleanEntry : shopListOpt.get().getIngredientsPurchaseStatusMap().entrySet()) {

            if (checkboxStatusUpdates.length == 1) {
                ingredientBooleanEntry.setValue(false);
            } else if (Arrays.stream(checkboxStatusUpdates).anyMatch(ingrName -> ingrName.equals(ingredientBooleanEntry.getKey().getName()))) {
                ingredientBooleanEntry.setValue(true);
            } else {
                ingredientBooleanEntry.setValue(false);
            }
        }

        this.shoppingListFromRecipesRepository.save(shopListOpt.get());

    }

    @Override
    @Transactional
    public void findAndDeleteShopListsInactiveByLastAccessedDate(LocalDate inactiveShoplistDate) {

        List<ShoppingListFromRecipes> byLastAccessedDateBefore = this.shoppingListFromRecipesRepository.findByLastAccessedDateBefore(inactiveShoplistDate);

        if (byLastAccessedDateBefore.isEmpty()) {
            return;
        }

        List<User> usersToResetShopListToNull = byLastAccessedDateBefore
                .stream()
                .map(ShoppingListFromRecipes::getCooker)
                .toList();

        for (User user : usersToResetShopListToNull) {

            user.setShoppingListFromRecipes(null);
            this.userService.save(user);

            //ako горното не работи да го изтрия и да ползвам долното. Т.е. ако репозиторито не си познава
            //извадения юзър.
//            this.userService.resetShopList(user);
        }

        this.shoppingListFromRecipesRepository.deleteAll(byLastAccessedDateBefore);
    }


    private Double calculateTotalQtyOfProductNeeded(Ingredient ingredient, Map<Recipe, Short> recipesSelectedWithDesiredServingsMap) {

        Double ingredientTotalQtyNeeded = 0.0;

        for (Map.Entry<Recipe, Short> recipeDesiredServingsEntry : recipesSelectedWithDesiredServingsMap.entrySet()) {

            boolean currentRecipeContainsIngredient = recipeDesiredServingsEntry.getKey().getRecipeIngredients().stream()
                    .map(RecipeIngredientWithDetails::getIngredient)
                    .anyMatch(i -> i.equals(ingredient));

            if (!currentRecipeContainsIngredient) {
                continue;
            }

            short recipeDesiredServings = recipeDesiredServingsEntry.getValue();
            IngredientMeasurementUnitEnum ingredientMainMUforCalcs = ingredient.getMainUnitOfMeasurement();
            IngredientMeasurementUnitEnum ingredientShopListDisplayMU = ingredient.getShoppingListDisplayUnitOfMeasurement();
            RecipeIngredientWithDetails recipeIngredientWithDetails = recipeDesiredServingsEntry.getKey()
                    .getRecipeIngredients()
                    .stream()
                    .filter(i -> i.getIngredient().equals(ingredient))
                    .findFirst()
                    .get();
            IngredientMeasurementUnitEnum ingredientMUUsedInRecipe = recipeIngredientWithDetails.getIngredientMeasurementUnitUsed();
            Double ingredientQtyInRecipeForDefaultServingsInMUUsedInRecipe = recipeIngredientWithDetails.getIngredientMeasurementTotalValue();
            short recipeDefaultServings = recipeDesiredServingsEntry.getKey().getServings();

            Double ingredientQtyTransformedInMainUnitOfMeasureForDefaultRecipeServings = ingredientQtyInRecipeForDefaultServingsInMUUsedInRecipe;

            if (!ingredientMUUsedInRecipe.equals(ingredientMainMUforCalcs)) {

                Float coeffiecientOfTransformationFromChosenMUToMainMU = ingredient.getIngredientAltMUVMap().get(ingredientMUUsedInRecipe);
                ingredientQtyTransformedInMainUnitOfMeasureForDefaultRecipeServings = ingredientQtyInRecipeForDefaultServingsInMUUsedInRecipe * coeffiecientOfTransformationFromChosenMUToMainMU;

            }

            Double qtyOfProductForCurrentRecipeForDesiredServingsQtyInMainMU = ingredientQtyTransformedInMainUnitOfMeasureForDefaultRecipeServings * recipeDesiredServings / recipeDefaultServings;

            Double qtyOfProductForCurrentRecipeForDesiredServingsQtyInShopListDisplayMU = qtyOfProductForCurrentRecipeForDesiredServingsQtyInMainMU;
            if (!ingredientShopListDisplayMU.equals(ingredientMainMUforCalcs)) {

                Float coefficientOfTransformationFromMainMUToShopListDisplayMU = ingredient.getIngredientAltMUVMap().get(ingredientShopListDisplayMU);
                qtyOfProductForCurrentRecipeForDesiredServingsQtyInShopListDisplayMU = qtyOfProductForCurrentRecipeForDesiredServingsQtyInMainMU * coefficientOfTransformationFromMainMUToShopListDisplayMU;

            }

            ingredientTotalQtyNeeded += qtyOfProductForCurrentRecipeForDesiredServingsQtyInShopListDisplayMU;
        }

        return ingredientTotalQtyNeeded;
    }

    private ShoppingListFromRecipes addIngredientsFromAddedRecipesInNeededIngredientsPurchaseStatusMap(ShoppingListFromRecipes userShopListFromRecipes) {

        Map<Ingredient, Boolean> currentIngredientsPurchaseStatusMap = userShopListFromRecipes.getIngredientsPurchaseStatusMap();

        Map<Recipe, Short> recipesSelectedWithDesiredServingsMap = userShopListFromRecipes.getRecipesSelectedWithDesiredServingsMap();

        for (Map.Entry<Recipe, Short> recipeQtyEntry : recipesSelectedWithDesiredServingsMap.entrySet()) {

            for (RecipeIngredientWithDetails recipeIngredient : recipeQtyEntry.getKey().getRecipeIngredients()) {

//                if (!currentIngredientsPurchaseStatusMap.containsKey(recipeIngredient.getIngredient())){
//                    currentIngredientsPurchaseStatusMap.put(recipeIngredient.getIngredient(), false);
//                }
//дори и да го съдържа трябва да променим ако е било true да стане false статуса на закупуване, затова няма нужда от проверка, то би трябвало да презапише продукта
                currentIngredientsPurchaseStatusMap.put(recipeIngredient.getIngredient(), false);

            }
        }

        return userShopListFromRecipes;
    }

    private ShoppingListFromRecipes removeIngredientsAfterRemovingRecipesInNeededIngredientsPurchaseStatusMap(ShoppingListFromRecipes userShopListFromRecipes) {

        Map<Ingredient, Boolean> currentIngredientsPurchaseStatusMap = userShopListFromRecipes.getIngredientsPurchaseStatusMap();

        List<Ingredient> ingredientsToRemoveFromCurrentIngredientsPurchaseStatusMap = new ArrayList<>();

        Map<Recipe, Short> recipesSelectedWithDesiredServingsMap = userShopListFromRecipes.getRecipesSelectedWithDesiredServingsMap();

        for (Ingredient thisIngredient : currentIngredientsPurchaseStatusMap.keySet()) {

            Optional<Recipe> anyRecipeContainingThisIngredient = recipesSelectedWithDesiredServingsMap.keySet().stream()
                    .filter(r -> r.getRecipeIngredients()
                            .stream()
                            .map(RecipeIngredientWithDetails::getIngredient)
                            .toList().contains(thisIngredient)).findAny();

            if (anyRecipeContainingThisIngredient.isEmpty()) {
                ingredientsToRemoveFromCurrentIngredientsPurchaseStatusMap.add(thisIngredient);
            }
        }

        ingredientsToRemoveFromCurrentIngredientsPurchaseStatusMap.forEach(currentIngredientsPurchaseStatusMap.keySet()::remove);

        return userShopListFromRecipes;

    }

//    private ShoppingListFromRecipes updateNeededIngredientsPurchaseStatusMap(ShoppingListFromRecipes userShopListFromRecipes) {
//
//        Map<Ingredient, Boolean> currentIngredientsPurchaseStatusMap = userShopListFromRecipes.getIngredientsPurchaseStatusMap();
//
//        List<Ingredient> ingredientsToRemoveFromCurrentIngredientsPurchaseStatusMap = new ArrayList<>();
//
//        Map<Recipe, Short> recipesSelectedWithDesiredServingsMap = userShopListFromRecipes.getRecipesSelectedWithDesiredServingsMap();
//
//        for (Map.Entry<Recipe, Short> recipeQtyEntry : recipesSelectedWithDesiredServingsMap.entrySet()) {
//
//            Long idOfRecipe = recipeQtyEntry.getKey().getId();
//
//            for (RecipeIngredientWithDetails recipeIngredient : recipeQtyEntry.getKey().getRecipeIngredients()) {
//
////                if (!currentIngredientsPurchaseStatusMap.containsKey(recipeIngredient.getIngredient())){
////                    currentIngredientsPurchaseStatusMap.put(recipeIngredient.getIngredient(), false);
////                }
////дори и да го съдържа трябва да променим ако е било true да стане false статуса на закупуване, затова няма нужда от проверка, то би трябвало да презапише продукта
//                currentIngredientsPurchaseStatusMap.put(recipeIngredient.getIngredient(), false);
//
//            }
//        }
//
//
//        for (Ingredient thisIngredient : currentIngredientsPurchaseStatusMap.keySet()) {
//
//            Optional<Recipe> anyRecipeContainingThisIngredient = recipesSelectedWithDesiredServingsMap.keySet().stream()
//                    .filter(r -> r.getRecipeIngredients()
//                            .stream()
//                            .map(RecipeIngredientWithDetails::getIngredient)
//                            .toList().contains(thisIngredient)).findAny();
//
//            if (anyRecipeContainingThisIngredient.isEmpty()){
//                ingredientsToRemoveFromCurrentIngredientsPurchaseStatusMap.add(thisIngredient);
//            }
//        }
//
//        ingredientsToRemoveFromCurrentIngredientsPurchaseStatusMap.forEach(currentIngredientsPurchaseStatusMap.keySet()::remove);
//
////        this.shoppingListFromRecipesRepository.save(userShopListFromRecipes);
//        return userShopListFromRecipes;
//    }
}
