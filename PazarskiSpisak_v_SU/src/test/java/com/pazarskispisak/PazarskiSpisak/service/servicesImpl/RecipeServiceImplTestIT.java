package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeIngredientWithDetailsAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.*;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import com.pazarskispisak.PazarskiSpisak.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecipeServiceImplTestIT {

    @Autowired
    private RecipeService recipeServiceToTest;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private IngredientService ingredientService;
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
    @DirtiesContext
//    @WithMockUser("test@test.com")
    void test_findByRecipeNameAndCurrentlyLoggedUser() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Recipe testRecipe = createTestRecipe(testCategorySM, testUser);

        Optional<Recipe> optEmpty = recipeServiceToTest.findByRecipeNameAndCurrentlyLoggedUser(testRecipe.getName(), "nonexistent@t.com");
        assertTrue(optEmpty.isEmpty());

        Optional<Recipe> recipeOpt = recipeServiceToTest.findByRecipeNameAndCurrentlyLoggedUser(testRecipe.getName(), testUser.getEmail());

        Assertions.assertTrue(recipeOpt.isPresent());
        assertEquals(testRecipe.getRecipeCategories(), recipeOpt.get().getRecipeCategories());
        assertEquals(testRecipe.getName(), recipeOpt.get().getName());
        assertEquals(testRecipe.getPublishedBy().getEmail(), recipeOpt.get().getPublishedBy().getEmail());
    }

    @Test
    @DirtiesContext
    void test_existsByRecipeCategory() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Recipe testRecipe = createTestRecipe(testCategorySM, testUser);

        boolean b = recipeServiceToTest.existsByRecipeCategory(RecipeCategoryEnum.APPETIZER);

        Assertions.assertTrue(b);
        assertFalse(() -> recipeServiceToTest.existsByRecipeCategory(RecipeCategoryEnum.BANITZA_BREAD));
    }

    @Test
    @DirtiesContext
    void test_deleteRecipeById() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        User testNotAuthor = createTestUser("another@t.com", "testAnotherDisplayName", userRoleUser);
        Recipe testRecipe = createTestRecipe(testCategorySM, testUser);

        assertEquals(testRecipe.getPublishedBy(), testUser);

        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> recipeServiceToTest.deleteRecipeById(222222L, testNotAuthor.getEmail()));

        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> recipeServiceToTest.deleteRecipeById(testRecipe.getId(), testNotAuthor.getEmail()));

        assertTrue(recipeServiceToTest.findById(testRecipe.getId()).isPresent());

        recipeServiceToTest.deleteRecipeById(testRecipe.getId(), testUser.getEmail());

        assertTrue(recipeServiceToTest.findById(testRecipe.getId()).isEmpty());

    }

    @Test
    @DirtiesContext
    void test_saveWithProducts() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Ingredient testIngredient1 = createTestIngredient("fish", testCategorySM);
        Ingredient testIngredient2 = createTestIngredient("oil", testCategorySM);
        RecipeAddDTO testRecipeAddDTO = createTestRecipeModel(testCategorySM, List.of(testIngredient1, testIngredient2));

        Long l = recipeServiceToTest.saveWithProducts(testRecipeAddDTO, testUser.getEmail());

        assertEquals(recipeRepository.count(), l);
        assertEquals(testUser.getEmail(), recipeRepository.findById(l).get().getPublishedBy().getEmail());

    }

    @Test
    @DirtiesContext
    void test_savePictureNameForRecipeInDB(){

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Recipe testRecipe = createTestRecipe(testCategorySM, testUser);

        recipeRepository.save(testRecipe);

        recipeServiceToTest.savePictureNameForRecipeInDB(testRecipe.getId(), testUser.getId(), "1_recipeName");

        Optional<Recipe> byId = recipeRepository.findById(testRecipe.getId());

        assertEquals("1_recipeName", byId.get().getPicture());
    }


    private RecipeAddDTO createTestRecipeModel(ItemCategorySupermarket itemCategorySM, List<Ingredient> ingrList) {

        RecipeAddDTO testRecipeAddDTO = new RecipeAddDTO();
        testRecipeAddDTO.setRecipeName("recipeName");
        testRecipeAddDTO.setRecipeSource("recSource");
        testRecipeAddDTO.setCookSteps("cook cook cook");
        testRecipeAddDTO.setServings((short) 2);
        testRecipeAddDTO.setCookTimeHH((short) 1);
        testRecipeAddDTO.setCookTimeMM((short) 15);
        testRecipeAddDTO.setNotes("note");
        testRecipeAddDTO.setPrepTimeDD((short) 1);
        testRecipeAddDTO.setPrepTimeHH((short) 3);
        testRecipeAddDTO.setPrepTimeMM((short) 30);
        testRecipeAddDTO.setCategories(Set.of("APPETIZER", "SAUCE"));
        testRecipeAddDTO.setRecipeIngredientWithDetailsAddDTOList(createListOfRecipeIngrDetailsDTO(itemCategorySM, ingrList));

        return testRecipeAddDTO;
    }

    private List<RecipeIngredientWithDetailsAddDTO> createListOfRecipeIngrDetailsDTO(ItemCategorySupermarket icsuperm, List<Ingredient> ingrList) {

        List<RecipeIngredientWithDetailsAddDTO> testIngrWithDetailsListForRecipeAddDTO = new ArrayList<>();

        RecipeIngredientWithDetailsAddDTO testRIWDAddDTO1 = new RecipeIngredientWithDetailsAddDTO();

        testRIWDAddDTO1.setIngredientId(ingrList.get(0).getId());
        testRIWDAddDTO1.setQty(6.7F);
        testRIWDAddDTO1.setIngredientMeasurementUnitEnum(IngredientMeasurementUnitEnum.TABLE_SPOON.name());

        testIngrWithDetailsListForRecipeAddDTO.add(testRIWDAddDTO1);

        RecipeIngredientWithDetailsAddDTO testRIWDAddDTO2 = new RecipeIngredientWithDetailsAddDTO();
        testRIWDAddDTO2.setIngredientId(ingrList.get(1).getId());
        testRIWDAddDTO2.setQty(6.7F);
        testRIWDAddDTO2.setIngredientMeasurementUnitEnum(IngredientMeasurementUnitEnum.TABLE_SPOON.name());

        testIngrWithDetailsListForRecipeAddDTO.add(testRIWDAddDTO2);

        return testIngrWithDetailsListForRecipeAddDTO;
    }

    private UserRoleEntity createUserRoleEntityUser() {

        UserRoleEntity testUserRoleUser = new UserRoleEntity();
        testUserRoleUser.setUserRole(UserRoleEnum.USER);

        userRolesRepository.save(testUserRoleUser);
        return testUserRoleUser;
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

        Ingredient savedIngredient = ingredientRepository.save(testIngredient);
//        ingredientService.save(testIngredient);

        return savedIngredient;
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

