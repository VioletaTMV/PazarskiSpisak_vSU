package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.entities.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.*;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@SpringBootTest
public class RecipeServiceImplTestIT {

    @Autowired
    private RecipeService recipeServiceToTest;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecipeIngredientsRepository recipeIngredientsRepository;
    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private UserService userService;


    @BeforeEach
    void setUp() {
        recipeRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        recipeRepository.deleteAll();
    }


    @Test
//    @WithMockUser("test@test.com")
    void test_findByRecipeNameAndCurrentlyLoggedUser() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        System.out.println(userRoleUser);
        User testUser = createTestUser(userRoleUser);
        System.out.println(testUser);
        Recipe testRecipe = createTestRecipe(testCategorySM, testUser);
        System.out.println(testRecipe);


        Optional<Recipe> recipeOpt = recipeServiceToTest.findByRecipeNameAndCurrentlyLoggedUser(testRecipe.getName(), testUser.getEmail());

        Assertions.assertTrue(recipeOpt.isPresent());
        Assertions.assertEquals(testRecipe.getRecipeCategories(), recipeOpt.get().getRecipeCategories());
        Assertions.assertEquals(testRecipe.getName(), recipeOpt.get().getName());
        Assertions.assertEquals(testRecipe.getPublishedBy().getEmail(), recipeOpt.get().getPublishedBy().getEmail());
    }

    private UserRoleEntity createUserRoleEntityUser() {

        UserRoleEntity testUserRoleUser = new UserRoleEntity();
        testUserRoleUser.setUserRole(UserRoleEnum.USER);

        userRolesRepository.save(testUserRoleUser);
        return testUserRoleUser;
    }

    private User createTestUser(UserRoleEntity userRole) {

        User testUser = new User();
        testUser.setEmail("test@t.com");
        testUser.setRegisteredOn(LocalDate.of(2023, 11, 1));
        testUser.setPassword("test12Q!");
        testUser.setDisplayNickname("testDisplayName");
        testUser.setUserRoles(Set.of(userRole));

        userRepository.save(testUser);
        return testUser;
    }

    private Recipe createTestRecipe(ItemCategorySupermarket categorySupermarket, User testUser) {
        Recipe testRecipe = new Recipe();
        testRecipe.setName("recipeName");
        testRecipe.setServings((short) 2);
        testRecipe.setCookSteps("cook cook cook");
        testRecipe.setVisible(true);
        testRecipe.setDatePublished(LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000));
        testRecipe.setDateLastModified(LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000));
        testRecipe.setRecipeCategories(Set.of(RecipeCategoryEnum.SAUCE, RecipeCategoryEnum.APPETIZER));
        testRecipe.setPublishedBy(testUser);

        Recipe testRSaved = recipeRepository.save(testRecipe);

        testRSaved.setRecipeIngredients(createSetOfRecipeIngrWithDetails(testRSaved, categorySupermarket));

        recipeRepository.save(testRSaved);
        return testRSaved;
    }

    private Set<RecipeIngredientWithDetails> createSetOfRecipeIngrWithDetails(Recipe testRecipe, ItemCategorySupermarket testC) {

        Set<RecipeIngredientWithDetails> testSetOfRecipeIngrWithDetails = new LinkedHashSet<>();

        RecipeIngredientWithDetails rid1 = new RecipeIngredientWithDetails(
                testRecipe,
                createTestIngredient("fish", testC),
                IngredientMeasurementUnitEnum.TABLE_SPOON,
                6.7);

        testSetOfRecipeIngrWithDetails.add(rid1);

        RecipeIngredientWithDetails rid2 = new RecipeIngredientWithDetails(
                testRecipe,
                createTestIngredient("oil", testC),
                IngredientMeasurementUnitEnum.TABLE_SPOON,
                6.7);

        testSetOfRecipeIngrWithDetails.add(rid2);

        recipeIngredientsRepository.saveAll(testSetOfRecipeIngrWithDetails);
        return testSetOfRecipeIngrWithDetails;
    }

    private Ingredient createTestIngredient(String testName, ItemCategorySupermarket testCategory) {
        Ingredient testIngredient = new Ingredient();
        testIngredient.setName(testName);
        testIngredient.setMainUnitOfMeasurement(IngredientMeasurementUnitEnum.GRAM);
        testIngredient.setShoppingListDisplayUnitOfMeasurement(IngredientMeasurementUnitEnum.GRAM);
        testIngredient.setItemCategory(testCategory);
//        testIngredient.setIngredientAltMUVMap(createIngrAltMUVMap());
        testIngredient.addAlternativeMeasurementUnitAndValueToMap(IngredientMeasurementUnitEnum.PIECE, 2.1F);
        testIngredient.addAlternativeMeasurementUnitAndValueToMap(IngredientMeasurementUnitEnum.TABLE_SPOON, 5F);

        ingredientRepository.save(testIngredient);

        return testIngredient;
    }

    private Map<IngredientMeasurementUnitEnum, Float> createIngrAltMUVMap() {

        Map<IngredientMeasurementUnitEnum, Float> testAltMUVMap = new LinkedHashMap<>();
        testAltMUVMap.put(IngredientMeasurementUnitEnum.PIECE, 2.1F);
        testAltMUVMap.put(IngredientMeasurementUnitEnum.TABLE_SPOON, 5F);

        return testAltMUVMap;
    }

    private ItemCategorySupermarket createTestCategory(String testItemCategoryName, short orderShopList) {

        ItemCategorySupermarket testItemCategory = new ItemCategorySupermarket();
        testItemCategory.setName(testItemCategoryName);
        testItemCategory.setFood(true);
        testItemCategory.setOrderInShoppingList(orderShopList);

        itemCategoryRepository.save(testItemCategory);
        return testItemCategory;
    }
}

