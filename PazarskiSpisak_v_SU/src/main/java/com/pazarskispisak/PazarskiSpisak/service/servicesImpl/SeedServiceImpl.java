package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.google.gson.Gson;
import com.pazarskispisak.PazarskiSpisak.models.dtos.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.models.entities.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static com.pazarskispisak.PazarskiSpisak.constants.ImportPathLegacyData.*;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserService userService;
    private final ItemCategoryService itemCategoryService;
    private final IngredientService ingredientService;
    //   private final IngredientMeasuresValuesService ingredientMeasuresValuesService;
    private final ItemService itemService;
    private final RecipeService recipeService;
    private final RecipeIngredientsService recipeIngredientsService;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Map<RecipeCategoryEnum, List<Integer>> categoriesWithListOfLegacyCookIdsMap = new HashMap<>();
    private final RecipeCategoryGroupService recipeCategoryGroupService;

    @Autowired
    public SeedServiceImpl(UserService userService, ItemCategoryService itemCategoryService, IngredientService ingredientService, ItemService itemService, RecipeService recipeService, RecipeIngredientsService recipeIngredientsService, ModelMapper modelMapper, Gson gson, RecipeCategoryGroupService recipeCategoryGroupService) {
        this.userService = userService;
        this.itemCategoryService = itemCategoryService;
        this.ingredientService = ingredientService;
//        this.ingredientMeasuresValuesService = ingredientMeasuresValuesService;
        this.itemService = itemService;
        this.recipeService = recipeService;
        this.recipeIngredientsService = recipeIngredientsService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.recipeCategoryGroupService = recipeCategoryGroupService;
    }


    @Override
    public void importUsers() throws FileNotFoundException {

        if (this.userService.areImported()) {
            return;
        }

        User userVilcho = new User();
        userVilcho.setEmail("vilcho@yahoo.com");
        userVilcho.setPassword("viliadmin");
        userVilcho.setDisplayNickname("PS");
        userVilcho.setLegacyUserLogname("vili");

        this.userService.save(userVilcho);

        ImportUserDTO[] importUserDTOS = this.gson.fromJson(new FileReader(importUsersPath.toFile()), ImportUserDTO[].class);

        List<User> usersToImportToDB = Arrays.stream(importUserDTOS)
                .filter(importUserDTO -> !importUserDTO.getEmail().equals("vilcho@yahoo.com"))
                .map(importUserDTO -> this.modelMapper.map(importUserDTO, User.class))
                .toList();

        this.userService.saveAll(usersToImportToDB);

    }

    @Override
    public void importItemCategories() throws FileNotFoundException {

        if (this.itemCategoryService.areImported()) {
            return;
        }


        ImportItemCategoryDTO[] importItemCategoryDTOS = this.gson.fromJson(new FileReader(importItemCategoriesPath.toFile()), ImportItemCategoryDTO[].class);

        List<ItemCategorySupermarket> itemCategoriesToSaveToDB = Arrays.stream(importItemCategoryDTOS)
                .map(importItemCategoryDTO -> this.modelMapper.map(importItemCategoryDTO, ItemCategorySupermarket.class))
                .toList();


        this.itemCategoryService.saveAll(itemCategoriesToSaveToDB);

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void importIngredientsAndTheirAltMeasurements() throws FileNotFoundException {

        if (this.ingredientService.areImported()){
          //  && this.ingredientMeasuresValuesService.areImported()) {
            return;
        }

        ImportIngredientDTO[] importIngredientDTOS = this.gson.fromJson(new FileReader(importIngredientsPath.toFile()), ImportIngredientDTO[].class);

        List<Ingredient> ingredientsToSaveToDB = new ArrayList<>();
        //      List<IngredientMeasuresValues> ingredientMeasuresValuesToSaveToDB = new ArrayList<>();

        for (ImportIngredientDTO importIngredientDTO : importIngredientDTOS) {

            Ingredient ingredient = modelMapper.map(importIngredientDTO, Ingredient.class);

            ItemCategorySupermarket currentItemCategorySupermarketBasedOnLegacy = this.itemCategoryService.getCurrentItemCategoryBasedOnLegacy(importIngredientDTO.getLegacyArtcategory());

            ingredient.setItemCategory(currentItemCategorySupermarketBasedOnLegacy);
            ingredient.setMainUnitOfMeasurement(importIngredientDTO.getArticleMeasureType() == 0 ?
                    IngredientMeasurementUnitEnum.GRAM :
                    IngredientMeasurementUnitEnum.MILLILITER);
            ingredient.setDefaultShoppingListDisplayUnitOfMeasurement();


//            if (!importIngredientDTO.getAlternativeMeasurementUnit().equals("")) {
//                IngredientMeasuresValues ingredientMeasuresValues = new IngredientMeasuresValues(
//                        new IngredientMeasuresValuesID(
//                                ingredient,
//                                IngredientMeasurementUnitEnum.getIngredientMeasurementUnitFromItsValue(importIngredientDTO.getAlternativeMeasurementUnit())),
//                        importIngredientDTO.getAlternativeMeasureValue());
//
//                ingredientMeasuresValuesToSaveToDB.add(ingredientMeasuresValues);
//            }

            if (!importIngredientDTO.getAlternativeMeasurementUnit().equals("")) {

                ingredient.addAlternativeMeasurementUnitAndValueToMap(
                        IngredientMeasurementUnitEnum.getIngredientMeasurementUnitFromItsValue(importIngredientDTO.getAlternativeMeasurementUnit()),
                        importIngredientDTO.getAlternativeMeasureValue());
            }

            ingredientsToSaveToDB.add(ingredient);
        }

        this.ingredientService.saveAll(ingredientsToSaveToDB);
        //      this.ingredientMeasuresValuesService.saveAll(ingredientMeasuresValuesToSaveToDB);

    }

    @Override
    public void importItems() throws FileNotFoundException {

        ImportItemDTO[] importItemDTOS = this.gson.fromJson(new FileReader(importItemsPath.toFile()), ImportItemDTO[].class);

        List<Item> itemsToSaveToDB = new ArrayList<>();

        for (ImportItemDTO importItemDTO : importItemDTOS) {

            if (this.itemService.findByName(importItemDTO.getName()).isPresent()) {
                return;
            }

            Item item = this.modelMapper.map(importItemDTO, Item.class);

            ItemCategorySupermarket currentItemCategorySupermarketBasedOnLegacy = this.itemCategoryService.getCurrentItemCategoryBasedOnLegacy(importItemDTO.getLegacyArtcategory());

            item.setItemCategory(currentItemCategorySupermarketBasedOnLegacy);

            itemsToSaveToDB.add(item);

        }

        this.itemService.saveAll(itemsToSaveToDB);

    }

    @Override
    public void importRecipes() throws FileNotFoundException {

        if (this.recipeService.areImported()) {
            return;
        }

        ImportRecipeDTO[] importRecipeDTOS = this.gson.fromJson(new FileReader(importRecipesPath.toFile()), ImportRecipeDTO[].class);

        fillCategoriesMapWithListOfLegacyCookIds();

        List<Recipe> recipesToSaveToDB = new ArrayList<>();

        for (ImportRecipeDTO importRecipeDTO : importRecipeDTOS) {

            Recipe recipe = this.modelMapper.map(importRecipeDTO, Recipe.class);

            recipe.setVisible(importRecipeDTO.isVisible() == 1 ? true : false);

            User recipePublisher;

            if (importRecipeDTO.getCook_userId() == 10 || importRecipeDTO.getCook_userId() == 4 || importRecipeDTO.getCook_userId() == 5) {
                recipePublisher = this.userService.findByEmail("vilcho@yahoo.com").get();
            } else {
                recipePublisher = this.userService.findByLegacyId(importRecipeDTO.getCook_userId());
            }

            recipe.setPublishedBy(recipePublisher);

            recipe.setDateLastModified(recipe.getDatePublished());

            addCategoriesForRecipe(recipe);

            recipesToSaveToDB.add(recipe);
        }

        this.recipeService.saveAll(recipesToSaveToDB);


    }

    private void addCategoriesForRecipe(Recipe recipe) {

        categoriesWithListOfLegacyCookIdsMap.forEach((key, value) -> {
            if (value.contains(recipe.getLegacyCookId())) {
                recipe.addRecipeCategory(key);
            }
        });
    }

    private void fillCategoriesMapWithListOfLegacyCookIds() throws FileNotFoundException {

        readCategoryFileAndAddToCategoriesMap(importSaladCategoryRecipesPath.toFile(), RecipeCategoryEnum.SALAD);
        readCategoryFileAndAddToCategoriesMap(importAppetizersCategoryRecipesPath.toFile(), RecipeCategoryEnum.APPETIZER);
        readCategoryFileAndAddToCategoriesMap(importSoupCategoryRecipesPath.toFile(), RecipeCategoryEnum.SOUP);
        readCategoryFileAndAddToCategoriesMap(importMainCourseCategoryRecipesPath.toFile(), RecipeCategoryEnum.MAIN_COURSE);
        readCategoryFileAndAddToCategoriesMap(importDessertCategoryRecipesPath.toFile(), RecipeCategoryEnum.DESSERT);
        readCategoryFileAndAddToCategoriesMap(importSideDishCategoryRecipesPath.toFile(), RecipeCategoryEnum.SIDE_DISH);
        readCategoryFileAndAddToCategoriesMap(importDrinkCategoryRecipesPath.toFile(), RecipeCategoryEnum.DRINK);
        readCategoryFileAndAddToCategoriesMap(importSandwichVarietyRecipesPath.toFile(), RecipeCategoryEnum.SANDWICH);
        readCategoryFileAndAddToCategoriesMap(importSauceVarietyRecipesPath.toFile(), RecipeCategoryEnum.SAUCE);
        readCategoryFileAndAddToCategoriesMap(importCakeCookieVarietyRecipesPath.toFile(), RecipeCategoryEnum.CAKE_COOKIE);
        readCategoryFileAndAddToCategoriesMap(importBanitzaVarietyRecipesPath.toFile(), RecipeCategoryEnum.BANITZA_BREAD);
        readCategoryFileAndAddToCategoriesMap(importPizzaVarietyRecipesPath.toFile(), RecipeCategoryEnum.PIZZA);
        readCategoryFileAndAddToCategoriesMap(importPreservesVarietyRecipesPath.toFile(), RecipeCategoryEnum.PRESERVES);
        readCategoryFileAndAddToCategoriesMap(importKidsVarietyRecipesPath.toFile(), RecipeCategoryEnum.KIDS);
        readCategoryFileAndAddToCategoriesMap(importPastaVarietyRecipesPath.toFile(), RecipeCategoryEnum.PASTA_RICE);
        readCategoryFileAndAddToCategoriesMap(importByIngredientBeefVealRecipesPath.toFile(), RecipeCategoryEnum.VEAL_BEEF);
        readCategoryFileAndAddToCategoriesMap(importByIngredientPorkRecipesPath.toFile(), RecipeCategoryEnum.PORK);
        readCategoryFileAndAddToCategoriesMap(importByIngredientPoultryRecipesPath.toFile(), RecipeCategoryEnum.POULTRY);
        readCategoryFileAndAddToCategoriesMap(importByIngredientRabbitRecipesPath.toFile(), RecipeCategoryEnum.RABBIT);
        readCategoryFileAndAddToCategoriesMap(importByIngredientLambMoutonRecipesPath.toFile(), RecipeCategoryEnum.LAMB_MUTTON);
        readCategoryFileAndAddToCategoriesMap(importByIngredientMincedMeatRecipesPath.toFile(), RecipeCategoryEnum.MINCED_MEAT);
        readCategoryFileAndAddToCategoriesMap(importByIngredientFishSeafoodRecipesPath.toFile(), RecipeCategoryEnum.FISH_SEAFOOD);
        readCategoryFileAndAddToCategoriesMap(importByIngredientEggsRecipesPath.toFile(), RecipeCategoryEnum.EGGS);
        readCategoryFileAndAddToCategoriesMap(importByIngredientKolbasiRecipesPath.toFile(), RecipeCategoryEnum.SAUSAGES_MEAT_DELICACY);
        readCategoryFileAndAddToCategoriesMap(importByIngredientGibletsRecipesPath.toFile(), RecipeCategoryEnum.GIBLETS);
        readCategoryFileAndAddToCategoriesMap(importByIngredientDairyProductsRecipesPath.toFile(), RecipeCategoryEnum.DAIRY_PRODUCTS);
        readCategoryFileAndAddToCategoriesMap(importByIngredientVeganRecipesPath.toFile(), RecipeCategoryEnum.VEGAN);


    }

    private void readCategoryFileAndAddToCategoriesMap(File file, RecipeCategoryEnum recipeCategoryEnum) throws FileNotFoundException {

        categoriesWithListOfLegacyCookIdsMap.put(recipeCategoryEnum, Arrays.stream(this.gson.fromJson(new FileReader(file), ImportCategoryForRecipeDTO[].class))
                .map(ImportCategoryForRecipeDTO::getLegacyCookIdForRecipeInCategory)
                .toList());

    }

    @Override
    public void importRecipeIngredients() throws FileNotFoundException {

        if (this.recipeIngredientsService.areImported()) {
            return;
        }

        ImportRecipeIngredientsDTO[] importRecipeIngredientsDTOS = this.gson.fromJson(new FileReader(importRecipeIngredientsPath.toFile()), ImportRecipeIngredientsDTO[].class);

        List<RecipeIngredientWithDetails> recipeIngredientWithDetailsToSaveToDB = new ArrayList<>();

        for (ImportRecipeIngredientsDTO importRecipeIngredientsDTO : importRecipeIngredientsDTOS) {

            Recipe recipe = this.recipeService.findByLegacyCookId(importRecipeIngredientsDTO.getLegacyCookId()).orElse(null);
            Ingredient ingredient = this.ingredientService.findByLegacyArticleId(importRecipeIngredientsDTO.getLegacyArticleId()).orElse(null);

            if (recipe == null || ingredient == null) {
                continue;
            }

//            RecipeIngredientID recipeIngredientID = new RecipeIngredientID(recipe.getId(), ingredient.getId());
            IngredientMeasurementUnitEnum ingredientMeasurementUnitUsedInRecipe = null;
            Double ingredientMeasurementTotalValue = null;

            if (importRecipeIngredientsDTO.getAlternativeMeasureValue() > 0.0) {
                ingredientMeasurementUnitUsedInRecipe = getExistingAlternativeMeasurementUnit(ingredient);
                ingredientMeasurementTotalValue = importRecipeIngredientsDTO.getAlternativeMeasureValue();
            } else {
                ingredientMeasurementUnitUsedInRecipe = ingredient.getMainUnitOfMeasurement();
                ingredientMeasurementTotalValue = importRecipeIngredientsDTO.getMainGrMlValue();
            }

//            RecipeIngredients recipeIngredient = new RecipeIngredients(recipeIngredientsID, ingredientMeasurementUnitUsedInRecipe, ingredientMeasurementTotalValue);
            RecipeIngredientWithDetails recipeIngredientWithDetails = new RecipeIngredientWithDetails(recipe, ingredient, ingredientMeasurementUnitUsedInRecipe, ingredientMeasurementTotalValue);

            recipeIngredientWithDetailsToSaveToDB.add(recipeIngredientWithDetails);

//            recipe.addRecipeIngredientWithDetails(recipe, ingredient, ingredientMeasurementUnitUsedInRecipe, ingredientMeasurementTotalValue);
        }

        this.recipeIngredientsService.saveAll(recipeIngredientWithDetailsToSaveToDB);


    }

    @Override
    public void fillRecipeCategoriesGroup() {

        if (this.recipeCategoryGroupService.isFilled()) {
            return;
        }

        RecipeCategoryGroup byCourse = new RecipeCategoryGroup();
        byCourse.setGroupName("По степен на сервиране");
        byCourse.setGroupDisplayOrder(1);
        byCourse.addRecipeCategory(RecipeCategoryEnum.SALAD);
        byCourse.addRecipeCategory(RecipeCategoryEnum.SOUP);
        byCourse.addRecipeCategory(RecipeCategoryEnum.APPETIZER);
        byCourse.addRecipeCategory(RecipeCategoryEnum.MAIN_COURSE);
        byCourse.addRecipeCategory(RecipeCategoryEnum.DESSERT);
        byCourse.addRecipeCategory(RecipeCategoryEnum.SIDE_DISH);
        byCourse.addRecipeCategory(RecipeCategoryEnum.DRINK);

        this.recipeCategoryGroupService.save(byCourse);

        RecipeCategoryGroup byType = new RecipeCategoryGroup();
        byType.setGroupName("По тип ястие");
        byType.setGroupDisplayOrder(2);
        byType.addRecipeCategory(RecipeCategoryEnum.SANDWICH);
        byType.addRecipeCategory(RecipeCategoryEnum.PASTA_RICE);
        byType.addRecipeCategory(RecipeCategoryEnum.SAUCE);
        byType.addRecipeCategory(RecipeCategoryEnum.CAKE_COOKIE);
        byType.addRecipeCategory(RecipeCategoryEnum.BANITZA_BREAD);
        byType.addRecipeCategory(RecipeCategoryEnum.KIDS);
        byType.addRecipeCategory(RecipeCategoryEnum.PRESERVES);
        byType.addRecipeCategory(RecipeCategoryEnum.VEGAN);

        this.recipeCategoryGroupService.save(byType);

        RecipeCategoryGroup byCookingMethod = new RecipeCategoryGroup();
        byCookingMethod.setGroupName("По метод на готвене");
        byCookingMethod.setGroupDisplayOrder(4);
        byCookingMethod.addRecipeCategory(RecipeCategoryEnum.FRYING);
        byCookingMethod.addRecipeCategory(RecipeCategoryEnum.STEAMING);
        byCookingMethod.addRecipeCategory(RecipeCategoryEnum.BOILING);
        byCookingMethod.addRecipeCategory(RecipeCategoryEnum.BAKING);
        byCookingMethod.addRecipeCategory(RecipeCategoryEnum.BBQ);
        byCookingMethod.addRecipeCategory(RecipeCategoryEnum.RAW);
        byCookingMethod.addRecipeCategory(RecipeCategoryEnum.BREADING);

        this.recipeCategoryGroupService.save(byCookingMethod);

        RecipeCategoryGroup byMainIngredient = new RecipeCategoryGroup();
        byMainIngredient.setGroupName("По основна съставка");
        byMainIngredient.setGroupDisplayOrder(3);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.VEGETABLES);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.LEGUMINOUS);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.PORK);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.VEAL_BEEF);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.LAMB_MUTTON);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.MINCED_MEAT);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.POULTRY);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.RABBIT);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.FISH_SEAFOOD);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.EGGS);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.DAIRY_PRODUCTS);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.GAME);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.DUCK_GOOSE);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.SAUSAGES_MEAT_DELICACY);
        byMainIngredient.addRecipeCategory(RecipeCategoryEnum.GIBLETS);

        this.recipeCategoryGroupService.save(byMainIngredient);

        RecipeCategoryGroup byCuisine = new RecipeCategoryGroup();
        byCuisine.setGroupName("По тип кухня");
        byCuisine.setGroupDisplayOrder(5);
        byCuisine.addRecipeCategory(RecipeCategoryEnum.FRENCH);
        byCuisine.addRecipeCategory(RecipeCategoryEnum.ITALIAN);
        byCuisine.addRecipeCategory(RecipeCategoryEnum.ASIAN);
        byCuisine.addRecipeCategory(RecipeCategoryEnum.MEDITERRANEAN);
        byCuisine.addRecipeCategory(RecipeCategoryEnum.AMERICAN);
        byCuisine.addRecipeCategory(RecipeCategoryEnum.MEXICAN);
        byCuisine.addRecipeCategory(RecipeCategoryEnum.BULGARIAN);

        this.recipeCategoryGroupService.save(byCuisine);

    }

    private IngredientMeasurementUnitEnum getExistingAlternativeMeasurementUnit(Ingredient ingredient) {

        Set<IngredientMeasurementUnitEnum> currentIngredientExistingAlternativeMeasurementUnits = ingredient.getIngredientAltMUVMap().keySet();

        IngredientMeasurementUnitEnum currentIngredientOnlyMeasureUnit = currentIngredientExistingAlternativeMeasurementUnits
                .stream()
                .findFirst()
                .orElse(null);

//        List<IngredientMeasuresValues> currentIngredientExistingAlternativeMeasurementUnits = this.ingredientMeasuresValuesService.findByIngredientMeasuresValuesIDIngredient(ingredient);
//
//        return currentIngredientExistingAlternativeMeasurementUnits.size() > 0 ?
//                currentIngredientExistingAlternativeMeasurementUnits.get(0).getIngredientMeasuresValuesID().getAlternativeMeasurementUnit() :
//                null;

        return currentIngredientOnlyMeasureUnit;
    }

}

