package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeShortInfoDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.ShopListProductsDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.ShopListRecipesDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.UserBasicDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.*;
import com.pazarskispisak.PazarskiSpisak.service.ShoppingListFromRecipesService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import com.pazarskispisak.PazarskiSpisak.validations.ValidRecipe;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ShoppingListFromRecipesServiceImplTestIT {

    @Autowired
    private ShoppingListFromRecipesService shopListServiceToTest;
    @Autowired
    private ShoppingListFromRecipesRepository shopListRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeIngredientsRepository recipeIngredientsRepository;


    @BeforeEach
    void setUp() {
        shopListRepository.deleteAll();
    }

//    @AfterEach
//    void tearDown() {
//        shopListRepository.deleteAll();
//    }

    @Test
    @DirtiesContext
//    @Commit
    void test_createNewShopListForUser() {

        UserBasicDTO testUserBasicDTO = createTestUserBasicDTO(1L, "testDisplayName");
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);

        shopListServiceToTest.createNewShopListForUser(testUserBasicDTO);

        User user = userService.findByDisplayNickname(testUserBasicDTO.getDisplayNickname()).get();
        ShoppingListFromRecipes shopLR = user.getShoppingListFromRecipes();

        Assertions.assertNotNull(shopLR);
        assertEquals(user.getDisplayNickname(), shopLR.getCooker().getDisplayNickname());
    }

    @Test
    @DirtiesContext
    @Transactional
    void test_addRecipeToCurrentUserShopList() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        Ingredient testIngredient1 = createTestIngredient("fish", testCategorySM);
        Ingredient testIngredient2 = createTestIngredient("oil", testCategorySM);
        UserBasicDTO testUserBasicDTO = createTestUserBasicDTO(1L, "testDisplayName");
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Recipe testRecipe = createTestRecipe("testR1", LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        Recipe testRecipe2 = createTestRecipe("testR2", LocalDateTime.of(2023, Month.OCTOBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        Recipe testRecipe3ToAddToShopList = createTestRecipe("testRToAdd", LocalDateTime.of(2023, Month.DECEMBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        ShoppingListFromRecipes testShoppingListFromRecipes = createTestShoppingList(testUser, List.of(testRecipe, testRecipe2));

        shopListServiceToTest.addRecipeToCurrentUserShopList(testUserBasicDTO, testRecipe3ToAddToShopList.getId());

        Assertions.assertTrue(
                testShoppingListFromRecipes.getRecipesSelectedWithDesiredServingsMap()
                        .containsKey(testRecipe3ToAddToShopList));

        assertEquals(3, shopListRepository.findByCookerId(testUser.getId()).get().getRecipesSelectedWithDesiredServingsMap().size());
    }

    @Test
    @DirtiesContext
    void test_updateShoppingListWithRecipesDesiredServingsQtyPossibleChanges() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserBasicDTO testUserBasicDTO = createTestUserBasicDTO(1L, "testDisplayName");
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Ingredient testIngredient1 = createTestIngredient("fish", testCategorySM);
        Ingredient testIngredient2 = createTestIngredient("oil", testCategorySM);
        Recipe testRecipe = createTestRecipe("testR1", LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        Recipe testRecipe2 = createTestRecipe("testR2", LocalDateTime.of(2023, Month.OCTOBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        ShoppingListFromRecipes testShoppingListFromRecipes = createTestShoppingList(testUser, List.of(testRecipe, testRecipe2));
        RecipeShortInfoDTO testRShorInfoDTO1 = createTestRecipeShortInfoDTO(testRecipe, (short) (testRecipe.getServings() + 2));
        RecipeShortInfoDTO testRShorInfoDTO2 = createTestRecipeShortInfoDTO(testRecipe2, (short) (testRecipe2.getServings() + 2));
        ShopListRecipesDTO testShopListRecipesDTO = createTestShopListRecipesDTO(testUserBasicDTO, List.of(testRShorInfoDTO1, testRShorInfoDTO2));

        shopListServiceToTest.updateShoppingListWithRecipesDesiredServingsQtyPossibleChanges(testShopListRecipesDTO);

        assertEquals(testRecipe.getServings() + (short) 2, (short) testShopListRecipesDTO.getShopListRecipes().get(0).getDesiredServings());
    }

    @Test
    @DirtiesContext
    void test_updateShoppingListWithRecipesDesiredServingsQtyPossibleChanges_returnsFalseWhenInvalidUserId() {

        UserBasicDTO testInvalidUserBasicDTO = createTestUserBasicDTO(9999L, "testDisplayName");
        RecipeShortInfoDTO testRShorInfoDTO1 = new RecipeShortInfoDTO();
        RecipeShortInfoDTO testRShorInfoDTO2 = new RecipeShortInfoDTO();
        ShopListRecipesDTO testShopListRecipesDTO = createTestShopListRecipesDTO(testInvalidUserBasicDTO, List.of(testRShorInfoDTO1, testRShorInfoDTO2));

        Assertions.assertFalse(shopListServiceToTest.updateShoppingListWithRecipesDesiredServingsQtyPossibleChanges(testShopListRecipesDTO));
    }

    @Test
    @DirtiesContext
    @Transactional
    void test_removeRecipeFromCurrentUserShopList_whenRemovingSecondOfTwoRecipesInShopList() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserBasicDTO testUserBasicDTO = createTestUserBasicDTO(1L, "testDisplayName");
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Ingredient testIngredient1 = createTestIngredient("fish", testCategorySM);
        Ingredient testIngredient2 = createTestIngredient("oil", testCategorySM);
        Recipe testRecipe = createTestRecipe("testR1", LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        Recipe testRecipe2 = createTestRecipe("testR2", LocalDateTime.of(2023, Month.OCTOBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        ShoppingListFromRecipes testShoppingListFromRecipes = createTestShoppingList(testUser, List.of(testRecipe, testRecipe2));
        RecipeShortInfoDTO testRShorInfoDTO1 = createTestRecipeShortInfoDTO(testRecipe, testRecipe.getServings());
        RecipeShortInfoDTO testRShorInfoDTO2 = createTestRecipeShortInfoDTO(testRecipe2, testRecipe2.getServings());
        ShopListRecipesDTO testRemainingAfterDeletionShopListRecipesDTO = createTestShopListRecipesDTO(testUserBasicDTO, List.of(testRShorInfoDTO1));

        shopListServiceToTest.removeRecipeFromCurrentUserShopList(testRemainingAfterDeletionShopListRecipesDTO);

        assertEquals(1, testShoppingListFromRecipes.getRecipesSelectedWithDesiredServingsMap().size());
        Assertions.assertTrue(testShoppingListFromRecipes.getRecipesSelectedWithDesiredServingsMap().containsKey(testRecipe));
        Assertions.assertFalse(testShoppingListFromRecipes.getRecipesSelectedWithDesiredServingsMap().containsKey(testRecipe2));

    }

    @Test
    @DirtiesContext
//    @Transactional
    void test_removeRecipeFromCurrentUserShopList_whenRemovingAllOfTheRecipesInShopList() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserBasicDTO testUserBasicDTO = createTestUserBasicDTO(1L, "testDisplayName");
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Ingredient testIngredient1 = createTestIngredient("fish", testCategorySM);
        Ingredient testIngredient2 = createTestIngredient("oil", testCategorySM);
        Recipe testRecipe = createTestRecipe("testR1", LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        Recipe testRecipe2 = createTestRecipe("testR2", LocalDateTime.of(2023, Month.OCTOBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        ShoppingListFromRecipes testShoppingListFromRecipes = createTestShoppingList(testUser, List.of(testRecipe, testRecipe2));
        testUser.setShoppingListFromRecipes(testShoppingListFromRecipes);
        userRepository.save(testUser);
        RecipeShortInfoDTO testRShorInfoDTO1 = createTestRecipeShortInfoDTO(testRecipe, testRecipe.getServings());
        RecipeShortInfoDTO testRShorInfoDTO2 = createTestRecipeShortInfoDTO(testRecipe2, testRecipe2.getServings());
        ShopListRecipesDTO testRemainingAfterDeletionShopListRecipesDTO = createTestShopListRecipesDTO(testUserBasicDTO, List.of());

        shopListServiceToTest.removeRecipeFromCurrentUserShopList(testRemainingAfterDeletionShopListRecipesDTO);

        assertNull(userService.findById(testUser.getId()).get().getShoppingListFromRecipes());
        Assertions.assertTrue(shopListRepository.findByCookerId(testUser.getId()).isEmpty());
    }

    @Test
    @DirtiesContext
    void test_getShopListProductsForUser() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Ingredient testIngredient1 = createTestIngredient("fish", testCategorySM);
        Ingredient testIngredient2 = createTestIngredient("oil", testCategorySM);
        Recipe testRecipe = createTestRecipe("testR1", LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        Recipe testRecipe2 = createTestRecipe("testR2", LocalDateTime.of(2023, Month.OCTOBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        ShoppingListFromRecipes testShoppingListFromRecipes = createTestShoppingList(testUser, List.of(testRecipe, testRecipe2));
        testUser.setShoppingListFromRecipes(testShoppingListFromRecipes);

        ShopListProductsDTO shopLProductsDTO = shopListServiceToTest.getShopListProductsForUser(testUser.getEmail());

        Assertions.assertEquals(testCategorySM.getName(),
                shopLProductsDTO.getIngredientsPurchaseStatusListByProductCategoryMap().keySet().stream().findFirst().get());
        Assertions.assertEquals(1,
                shopLProductsDTO.getIngredientsPurchaseStatusListByProductCategoryMap().size());
        Assertions.assertEquals(2,
                shopLProductsDTO.getIngredientsPurchaseStatusListByProductCategoryMap().get(testCategorySM.getName()).size());

    }

    @Test
    @DirtiesContext
    @Transactional
    void test_updateCheckedStatusOfProductsBought() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Ingredient testIngredient1 = createTestIngredient("fish", testCategorySM);
        Ingredient testIngredient2 = createTestIngredient("oil", testCategorySM);
        Recipe testRecipe = createTestRecipe("testR1", LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        Recipe testRecipe2 = createTestRecipe("testR2", LocalDateTime.of(2023, Month.OCTOBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        ShoppingListFromRecipes testShoppingListFromRecipes = createTestShoppingList(testUser, List.of(testRecipe, testRecipe2));
        //  testUser.setShoppingListFromRecipes(testShoppingListFromRecipes);
        String[] testCheckboxStatusUpdatesChecked = new String[]{"dummyCheckboxStringAlwaysPresent", "fish"};

        shopListServiceToTest.updateCheckedStatusOfProductsBought(testCheckboxStatusUpdatesChecked, testUser.getEmail(), null);

        Assertions.assertEquals(true,
                shopListRepository.findByCookerEmail(testUser.getEmail()).get()
                        .getIngredientsPurchaseStatusMap()
                        .get(testIngredient1));
        Assertions.assertEquals(false,
                shopListRepository.findByCookerEmail(testUser.getEmail()).get()
                        .getIngredientsPurchaseStatusMap()
                        .get(testIngredient2));
    }

    @Test
    @DirtiesContext
    @Transactional
    void test_updateCheckedStatusOfProductsBought_whenNothingIsChecked() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Ingredient testIngredient1 = createTestIngredient("fish", testCategorySM);
        Ingredient testIngredient2 = createTestIngredient("oil", testCategorySM);
        Recipe testRecipe = createTestRecipe("testR1", LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        Recipe testRecipe2 = createTestRecipe("testR2", LocalDateTime.of(2023, Month.OCTOBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        ShoppingListFromRecipes testShoppingListFromRecipes = createTestShoppingList(testUser, List.of(testRecipe, testRecipe2));
        //  testUser.setShoppingListFromRecipes(testShoppingListFromRecipes);
        String[] testCheckboxStatusUpdatesChecked = new String[]{"dummyCheckboxStringAlwaysPresent"};

        shopListServiceToTest.updateCheckedStatusOfProductsBought(testCheckboxStatusUpdatesChecked, testUser.getEmail(), null);

        Assertions.assertEquals(false,
                shopListRepository.findByCookerEmail(testUser.getEmail()).get()
                        .getIngredientsPurchaseStatusMap()
                        .get(testIngredient1));
        Assertions.assertEquals(false,
                shopListRepository.findByCookerEmail(testUser.getEmail()).get()
                        .getIngredientsPurchaseStatusMap()
                        .get(testIngredient2));
    }

    @Test
    @DirtiesContext
    void test_findAndDeleteShopListsInactiveByLastAccessedDate() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Ingredient testIngredient1 = createTestIngredient("fish", testCategorySM);
        Ingredient testIngredient2 = createTestIngredient("oil", testCategorySM);
        Recipe testRecipe = createTestRecipe("testR1", LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        Recipe testRecipe2 = createTestRecipe("testR2", LocalDateTime.of(2023, Month.OCTOBER, 3, 6, 30, 40, 50000), testCategorySM, testUser, List.of(testIngredient1, testIngredient2));
        ShoppingListFromRecipes testShoppingListFromRecipes = createTestShoppingList(testUser, List.of(testRecipe, testRecipe2));
        testUser.setShoppingListFromRecipes(testShoppingListFromRecipes);
        userRepository.save(testUser);

        shopListServiceToTest.findAndDeleteShopListsInactiveByLastAccessedDate(LocalDate.of(2023, 11, 21));

        Assertions.assertNull(userService.findByEmail(testUser.getEmail()).get().getShoppingListFromRecipes());

    }


    private ShopListRecipesDTO createTestShopListRecipesDTO(UserBasicDTO testUserBasicDTO,
                                                            List<RecipeShortInfoDTO> testRSIDTOs) {

        ShopListRecipesDTO testShopListRecipesDTO = new ShopListRecipesDTO();
        testShopListRecipesDTO.setUserBasicDTO(testUserBasicDTO);
        testShopListRecipesDTO.setShopListRecipes(testRSIDTOs);

        return testShopListRecipesDTO;
    }

    private RecipeShortInfoDTO createTestRecipeShortInfoDTO(Recipe testRecipe, Short desiredServings) {

        RecipeShortInfoDTO testRShortInfoDTO = new RecipeShortInfoDTO();

        testRShortInfoDTO.setId(testRecipe.getId());
        testRShortInfoDTO.setName(testRecipe.getName());
        testRShortInfoDTO.setServings(testRecipe.getServings());
        testRShortInfoDTO.setDesiredServings(desiredServings);

        return testRShortInfoDTO;
    }

    private ShoppingListFromRecipes createTestShoppingList(User user, List<Recipe> recipeList) {

        ShoppingListFromRecipes testShopList = new ShoppingListFromRecipes();
        testShopList.setCooker(user)
                .setLastAccessedDate(LocalDate.of(2023, 11, 20))
                .setRecipesSelectedWithDesiredServingsMap(createTestRecipesSelectedWithDesiredServingsMap(recipeList))
                .setIngredientsPurchaseStatusMap(createTestIngredientsPurchaseStatusMap(recipeList));

        shopListRepository.save(testShopList);
        return testShopList;
    }

    private Map<Ingredient, Boolean> createTestIngredientsPurchaseStatusMap(List<Recipe> recipeList) {

        Map<Ingredient, Boolean> testIngredientsPurchaseStatusMap = new HashMap<>();

        for (int i = 0; i < recipeList.size(); i++) {
            List<Ingredient> testCurrIngrList = recipeList.get(i).getRecipeIngredients()
                    .stream()
                    .map(RecipeIngredientWithDetails::getIngredient)
                    .toList();
            for (int j = 0; j < testCurrIngrList.size(); j++) {
                Ingredient currentTestIngr = testCurrIngrList.get(j);

                if (!testIngredientsPurchaseStatusMap.containsKey(currentTestIngr)) {
                    testIngredientsPurchaseStatusMap.put(currentTestIngr, false);
                }
            }
        }
        return testIngredientsPurchaseStatusMap;
    }

    private Map<Recipe, Short> createTestRecipesSelectedWithDesiredServingsMap(List<Recipe> recipeList) {

        Map<Recipe, Short> recipesWithDesiredServings = new LinkedHashMap<>();

        for (int i = 0; i < recipeList.size(); i++) {
            recipesWithDesiredServings.put(recipeList.get(i), recipeList.get(i).getServings());
        }
        return recipesWithDesiredServings;
    }

    private Recipe createTestRecipe(String recipeName,
                                    LocalDateTime localDateTime,
                                    ItemCategorySupermarket categorySupermarket,
                                    User testUser,
                                    List<Ingredient> ingredientList) {
        Recipe testRecipe = new Recipe();
        testRecipe.setName(recipeName);
        testRecipe.setServings((short) 2);
        testRecipe.setCookSteps("cook cook cook");
        testRecipe.setVisible(true);
        testRecipe.setRecipeSource("recSource");
        testRecipe.setNotes("note");
        testRecipe.setPrepTime(Duration.ofHours(2));
        testRecipe.setCookTime(Duration.ofMinutes(30));
        testRecipe.setDatePublished(localDateTime);
        testRecipe.setDateLastModified(localDateTime);
        testRecipe.setRecipeCategories(Set.of(RecipeCategoryEnum.SAUCE, RecipeCategoryEnum.APPETIZER));
        testRecipe.setPublishedBy(testUser);

        recipeRepository.save(testRecipe);

//        testRecipe.addRecipeIngredientWithDetails(testRecipe, ingredientList.get(0), IngredientMeasurementUnitEnum.TABLE_SPOON, 55.8);
//        testRecipe.addRecipeIngredientWithDetails(testRecipe, ingredientList.get(1), IngredientMeasurementUnitEnum.GRAM, 8.8);

        testRecipe.setRecipeIngredients(createSetOfRecipeIngrWithDetails(testRecipe, categorySupermarket,
                ingredientList));

        return testRecipe;
    }

    private Set<RecipeIngredientWithDetails> createSetOfRecipeIngrWithDetails(Recipe testRecipe, ItemCategorySupermarket testC, List<Ingredient> ingrList) {

        Set<RecipeIngredientWithDetails> testSetOfRecipeIngrWithDetails = new LinkedHashSet<>();

        RecipeIngredientWithDetails rid1 = new RecipeIngredientWithDetails(
                testRecipe,
                ingrList.get(0),
                IngredientMeasurementUnitEnum.TABLE_SPOON,
                6.7);

        testSetOfRecipeIngrWithDetails.add(rid1);

        RecipeIngredientWithDetails rid2 = new RecipeIngredientWithDetails(
                testRecipe,
                ingrList.get(1),
                IngredientMeasurementUnitEnum.TABLE_SPOON,
                6.7);

        testSetOfRecipeIngrWithDetails.add(rid2);

        recipeIngredientsRepository.saveAll(testSetOfRecipeIngrWithDetails);
        return testSetOfRecipeIngrWithDetails;
    }

    private ItemCategorySupermarket createTestCategory(String testItemCategoryName, short orderShopList) {

        ItemCategorySupermarket testItemCategory = new ItemCategorySupermarket();
        testItemCategory.setName(testItemCategoryName);
        testItemCategory.setFood(true);
        testItemCategory.setOrderInShoppingList(orderShopList);

        itemCategoryRepository.save(testItemCategory);
        return testItemCategory;
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

        Ingredient savedIngredient = ingredientRepository.save(testIngredient);
//        ingredientService.save(testIngredient);

        return savedIngredient;
    }

    private ShoppingListFromRecipes createTestShopListWithUserOnly(User testUser) {

        ShoppingListFromRecipes testShopListWithUserOnly = new ShoppingListFromRecipes();
        testShopListWithUserOnly.setCooker(testUser);

        return testShopListWithUserOnly;
    }

    private User createTestUser(String email, String displayNickName, UserRoleEntity userRole) {

        User testUser = new User();
        testUser.setEmail(email);
        testUser.setRegisteredOn(LocalDate.of(2023, 11, 1));
        testUser.setPassword("test12Q!");
        testUser.setDisplayNickname(displayNickName);
        testUser.setUserRoles(Set.of(userRole));

        userRepository.save(testUser);
        return testUser;
    }

    private UserRoleEntity createUserRoleEntityUser() {

        UserRoleEntity testUserRoleUser = new UserRoleEntity();
        testUserRoleUser.setUserRole(UserRoleEnum.USER);

        userRolesRepository.save(testUserRoleUser);
        return testUserRoleUser;
    }

    private UserBasicDTO createTestUserBasicDTO(Long id, String nickName) {

        UserBasicDTO testUserBasicDTO = new UserBasicDTO();
        testUserBasicDTO.setId(id);
        testUserBasicDTO.setDisplayNickname(nickName);

        return testUserBasicDTO;

    }

}
