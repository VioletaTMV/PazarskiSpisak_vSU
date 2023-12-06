package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeShortInfoDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.ShoppingListFromRecipesRepository;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import com.pazarskispisak.PazarskiSpisak.service.ShoppingListFromRecipesService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import jakarta.validation.constraints.AssertFalse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingListFromRecipesServiceImplTest {

    private ShoppingListFromRecipesServiceImpl serviceToTest;

    @Mock
    private ShoppingListFromRecipesRepository mockedShoppingListFromRecipesRepository;
    @Mock
    private ModelMapper mockedModelMapper;
    @Mock
    private UserService mockedUserService;
    @Mock
    private RecipeService mockedRecipeService;
    @Mock
    private ItemCategoryService mockedItemCategoryService;
    private UserRoleEntity userRoleUser;
    private User testUser;
    private ItemCategorySupermarket testCategorySM;
    private Recipe testRecipe;
    private Recipe testRecipe2;
    private ShoppingListFromRecipes testShoppingListFromRecipes;





    @BeforeEach
    void setUp() {
        serviceToTest = new ShoppingListFromRecipesServiceImpl(
                mockedShoppingListFromRecipesRepository,
                mockedModelMapper,
                mockedUserService,
                mockedRecipeService,
                mockedItemCategoryService);

        userRoleUser = createUserRoleEntityUser();
        testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        testCategorySM = createTestCategory("fish and seafood", (short) 1);
        testRecipe = createTestRecipe(
                "recipeName",
                LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000),
                testCategorySM,
                testUser);
        testRecipe2 = createTestRecipe(
                "recipeName2",
                LocalDateTime.of(2023, Month.OCTOBER, 30, 6, 30, 40, 50000),
                testCategorySM,
                testUser);
        testShoppingListFromRecipes = createTestShoppingList(testUser, List.of(testRecipe, testRecipe2));

    }

    @Test
    void findByCookerId() {
        //Arrange
        when(mockedShoppingListFromRecipesRepository.findByCookerId(1L))
                .thenReturn(Optional.of(testShoppingListFromRecipes));
        //Act
        Optional<ShoppingListFromRecipes> byCookerId = serviceToTest.findByCookerId(1L);
        //Assert
        assertTrue(byCookerId.isPresent());
    }

    @Test
    void findByCookerId_nonExisting() {

        assertFalse(serviceToTest.findByCookerId(3333L).isPresent());
    }

    @Test
    void test_currentUserHasRecipesInShoppingList_nonExisting(){
        assertFalse(serviceToTest.currentUserHasRecipesInShoppingList(3333L));
    }

    @Test
    void test_currentUserHasRecipesInShoppingList_existing(){

        when(mockedShoppingListFromRecipesRepository.findByCookerId(1L))
                .thenReturn(Optional.of(testShoppingListFromRecipes));

        boolean b = serviceToTest.currentUserHasRecipesInShoppingList(1L);

        assertTrue(b);
    }

    @Test
    void test_getRecipesShortInfoWithDesiredServingsList(){

        RecipeShortInfoDTO testRShorInfoDTO1 = createTestRecipeShortInfoDTO(testRecipe);
        RecipeShortInfoDTO testRShorInfoDTO2 = createTestRecipeShortInfoDTO(testRecipe2);
        List<RecipeShortInfoDTO> testListOfRecipeShortInfoDTO = List.of(testRShorInfoDTO1, testRShorInfoDTO2);

        when(mockedShoppingListFromRecipesRepository.findByCookerId(1L))
                .thenReturn(Optional.of(testShoppingListFromRecipes));
//        doReturn(testShoppingListFromRecipes)
//                .when(mockedShoppingListFromRecipesRepository.findByCookerId(1L).get());
        when(mockedModelMapper.map(testRecipe, RecipeShortInfoDTO.class))
                .thenReturn(testRShorInfoDTO1);
        when(mockedModelMapper.map(testRecipe2, RecipeShortInfoDTO.class))
                .thenReturn(testRShorInfoDTO2);

        List<RecipeShortInfoDTO> rSIWDSL = serviceToTest.getRecipesShortInfoWithDesiredServingsList(1L);

        assertEquals(2, rSIWDSL.size());
        assertEquals(testRShorInfoDTO1, rSIWDSL.get(0));
        assertEquals(testRShorInfoDTO2, rSIWDSL.get(1));
    }



    private RecipeShortInfoDTO createTestRecipeShortInfoDTO(Recipe testRecipe) {

        RecipeShortInfoDTO testRShortInfoDTO = new RecipeShortInfoDTO();

        testRShortInfoDTO.setName(testRecipe.getName());
        testRShortInfoDTO.setServings(testRecipe.getServings());
        testRShortInfoDTO.setDesiredServings(testRecipe.getServings());

        return testRShortInfoDTO;
    }

//    private List<RecipeShortInfoDTO> createTestListOfRSIDTO(List<Recipe> recipeList) {
//
//        List<RecipeShortInfoDTO> testListOfRSIDTO= new ArrayList<>();
//
//        for (int i = 0; i < recipeList.size(); i++) {
//            RecipeShortInfoDTO testRecipeSIDTO = new RecipeShortInfoDTO();
//           testRecipeSIDTO.setName(recipeList.get(i).getName());
//           testRecipeSIDTO.setServings(recipeList.get(i).getServings());
//           testRecipeSIDTO.setDesiredServings(recipeList.get(i).getServings());
//
//           testListOfRSIDTO.add(testRecipeSIDTO);
//        }
//
//       return testListOfRSIDTO;
//    }

    private ShoppingListFromRecipes createTestShoppingList(User user, List<Recipe> recipeList) {

        ShoppingListFromRecipes testShopList = new ShoppingListFromRecipes();
        testShopList.setCooker(user)
                .setLastAccessedDate(LocalDate.of(2023, 11, 20))
                .setRecipesSelectedWithDesiredServingsMap(createTestRecipesSelectedWithDesiredServingsMap(recipeList))
                .setIngredientsPurchaseStatusMap(createTestIngredientsPurchaseStatusMap(recipeList));

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
                                    ItemCategorySupermarket categorySupermarket, User testUser) {
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
        testRecipe.setRecipeIngredients(createSetOfRecipeIngrWithDetails(testRecipe, categorySupermarket));

        return testRecipe;
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

        return testSetOfRecipeIngrWithDetails;
    }

    private Ingredient createTestIngredient(String testName, ItemCategorySupermarket testCategory) {
        Ingredient testIngredient = new Ingredient();
        testIngredient.setName(testName);
        testIngredient.setMainUnitOfMeasurement(IngredientMeasurementUnitEnum.GRAM);
        testIngredient.setShoppingListDisplayUnitOfMeasurement(IngredientMeasurementUnitEnum.GRAM);
        testIngredient.setItemCategory(testCategory);
        testIngredient.addAlternativeMeasurementUnitAndValueToMap(IngredientMeasurementUnitEnum.PIECE, 2.1F);
        testIngredient.addAlternativeMeasurementUnitAndValueToMap(IngredientMeasurementUnitEnum.TABLE_SPOON, 5F);

        return testIngredient;
    }

    private ItemCategorySupermarket createTestCategory(String testItemCategoryName, short orderShopList) {

        ItemCategorySupermarket testItemCategory = new ItemCategorySupermarket();
        testItemCategory.setName(testItemCategoryName);
        testItemCategory.setFood(true);
        testItemCategory.setOrderInShoppingList(orderShopList);

        return testItemCategory;
    }

    private UserRoleEntity createUserRoleEntityUser() {

        UserRoleEntity testUserRoleUser = new UserRoleEntity();
        testUserRoleUser.setUserRole(UserRoleEnum.USER);

        return testUserRoleUser;
    }

    private User createTestUser(String email, String displayNickName, UserRoleEntity userRole) {

        User testUser = new User();
        testUser.setEmail(email);
        testUser.setRegisteredOn(LocalDate.of(2023, 11, 1));
        testUser.setPassword("test12Q!");
        testUser.setDisplayNickname(displayNickName);
        testUser.setUserRoles(Set.of(userRole));

        return testUser;
    }
}