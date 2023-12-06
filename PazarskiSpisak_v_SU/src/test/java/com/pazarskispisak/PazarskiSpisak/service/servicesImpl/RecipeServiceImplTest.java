package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.*;
import com.pazarskispisak.PazarskiSpisak.models.entities.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.RecipeRepository;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeIngredientsService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import com.pazarskispisak.PazarskiSpisak.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    private RecipeServiceImpl serviceToTest;

    @Mock
    private RecipeRepository mockedRecipeRepository;
    @Mock
    private ModelMapper mockedModelMapper;
    @Mock
    private UserService mockedUserService;
    @Mock
    private IngredientService mockedIngredientService;
    @Mock
    private RecipeIngredientsService mockedRecipeIngredientsService;
    private ItemCategorySupermarket testCategorySM;
    private UserRoleEntity userRoleUser;
    private User testUser;
    private Recipe testRecipe;
    private RecipeViewDTO testRecipeViewDTO;


    @BeforeEach
    void setUp() {
        serviceToTest = new RecipeServiceImpl(
                mockedRecipeRepository,
                mockedModelMapper,
                mockedUserService,
                mockedIngredientService,
                mockedRecipeIngredientsService);

        testCategorySM = createTestCategory("fish and seafood", (short) 1);
        userRoleUser = createUserRoleEntityUser();
        testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        testRecipe = createTestRecipe(
                "recipeName",
                LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000),
                testCategorySM,
                testUser);
        testRecipeViewDTO = createTestRecipeViewDTO("recipeName", "3/11/2023", testUser);

    }

    @Test
    void test_getByRecipeId() {
        //Arrange
        RecipeViewDTO testRecipeViewDTObeforeManualSettings = createTestRecipeViewDTObefore(testUser);

                when(mockedRecipeRepository.findById(testUser.getId()))
                .thenReturn(Optional.of(testRecipe));
        when(mockedModelMapper.map(Optional.of(testRecipe), RecipeViewDTO.class))
                .thenReturn(testRecipeViewDTObeforeManualSettings);

        //Act
        RecipeViewDTO testResultingRecipeViewDTO = serviceToTest.getByRecipeId(testRecipe.getId());

        //Assert
        assertThrows(ObjectNotFoundException.class,
                () -> serviceToTest.getByRecipeId(22222L));
        assertEquals(testRecipe.getName(), testRecipeViewDTO.getName());
        assertEquals(testRecipe.getPublishedBy().getDisplayNickname(), testRecipeViewDTO.getPublishedBy());
        assertEquals("30min", testRecipeViewDTO.getCookTime());
    }

    @Test
    void test_findAllByCategoryOrderedByDateLastModified() {
        //Arrange
        Recipe anotherTestRecipe = createTestRecipe(
                "recipeName2",
                LocalDateTime.of(2023, Month.OCTOBER, 30, 6, 30, 40, 50000),
                testCategorySM,
                testUser);
        RecipeViewDTO testRecipeViewDTOShort1 = createTestRecipeViewDTO("recipeName", "3/11/2023", testUser);
        RecipeViewDTO testRecipeViewDTOShort2 = createTestRecipeViewDTO("recipeName2", "30/10/2023", testUser);

        when(mockedRecipeRepository.findByRecipeCategoriesContainingOrderByDateLastModifiedDesc(RecipeCategoryEnum.APPETIZER))
                .thenReturn(List.of(anotherTestRecipe, testRecipe));
        when(mockedModelMapper.map(testRecipe, RecipeViewDTO.class))
                .thenReturn(testRecipeViewDTOShort1);
        when(mockedModelMapper.map(anotherTestRecipe, RecipeViewDTO.class))
                .thenReturn(testRecipeViewDTOShort2);

        //Act
        Set<RecipeViewDTO> recipeViewDTOS = serviceToTest.findAllByCategoryOrderedByDateLastModified(RecipeCategoryEnum.APPETIZER);

        //Assert
        assertEquals(2, recipeViewDTOS.size());
        assertEquals(testRecipeViewDTOShort2.getName(), recipeViewDTOS.stream().toList().get(0).getName());

    }

    @Test
    void test_findAllByCategoryOrderedByDateLastModified_whenInvalidCategory() {
        //Act
        Set<RecipeViewDTO> recipeViewDTOS = serviceToTest.findAllByCategoryOrderedByDateLastModified(RecipeCategoryEnum.DRINK);

        //Assert
        assertEquals(0, recipeViewDTOS.size());
    }

    @Test
    void test_isCurrentUserAllowedToUploadPictureForCurrentRecipe() {
        //Arrange
        RecipePictureAddDTO testRecipePictureAddDTO = createRecipePictureAddDTO();
        UserBasicDTO testUserBasicDTO = createTestUserBasicDTO(1L, "testDisplayName");
        UserBasicDTO testUserBasicDTONonEx = createTestUserBasicDTO(333L, "testNonExName");

        when(mockedRecipeRepository.findById(testUserBasicDTO.getId()))
                .thenReturn(Optional.of(testRecipe));
        //Act
        boolean nonEx = serviceToTest.isCurrentUserAllowedToUploadPictureForCurrentRecipe(testUserBasicDTONonEx, testRecipePictureAddDTO);
        //Assert
        assertFalse(nonEx);
        assertNotEquals(testUserBasicDTONonEx.getId(), testRecipePictureAddDTO.getRecipePublishedById());

        //Act
        boolean b = serviceToTest.isCurrentUserAllowedToUploadPictureForCurrentRecipe(testUserBasicDTO, testRecipePictureAddDTO);
        //Assert
        assertTrue(b);
        assertEquals(testUserBasicDTO.getId(), testRecipePictureAddDTO.getRecipePublishedById());

    }

    @Test
    void test_getRecipePictureAddDTO() {

        RecipePictureAddDTO testRecipePictureAddDTO = createRecipePictureAddDTO();

        when(mockedRecipeRepository.findById(1L))
                .thenReturn(Optional.of(testRecipe));

        when(mockedModelMapper.map(Optional.of(testRecipe), RecipePictureAddDTO.class))
                .thenReturn(testRecipePictureAddDTO);

        //Act
        RecipePictureAddDTO testRPAddDTO = serviceToTest.getRecipePictureAddDTO(1L);
        //Assert
        assertEquals(testRPAddDTO.getRecipeName(), testRecipe.getName());
    }

    @Test
    void test_uploadPictureToDirAndGetFileName() throws IOException {
        RecipePictureAddDTO testRecipePictureAddDTO = createRecipePictureAddDTO();
        UserBasicDTO testUserBasicDTO = createTestUserBasicDTO(1L, "testDisplayName");

        //Act
        String testFileName = serviceToTest.uploadPictureToDirAndGetFileName(testRecipePictureAddDTO.getMultipartFile(), testUserBasicDTO, testRecipePictureAddDTO);

        //Assert
        assertEquals(testUserBasicDTO.getId() + "_" + testRecipePictureAddDTO.getRecipeName(), testFileName);
    }

    private UserBasicDTO createTestUserBasicDTO(Long id, String nickName) {

        UserBasicDTO testUserBasicDTO = new UserBasicDTO();
        testUserBasicDTO.setId(id);
        testUserBasicDTO.setDisplayNickname(nickName);

        return testUserBasicDTO;

    }

    private RecipePictureAddDTO createRecipePictureAddDTO() {

        MockMultipartFile testFile
                = new MockMultipartFile(
                "testMultiPartFile",
                "shopping_list.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "{url(../images/shopping_list.jpg}".getBytes());

        RecipePictureAddDTO testRecipePictureAddDTO = new RecipePictureAddDTO();
        testRecipePictureAddDTO.setRecipeId(1L);
        testRecipePictureAddDTO.setRecipeName("recipeName");
        testRecipePictureAddDTO.setRecipePublishedById(1L);
        testRecipePictureAddDTO.setMultipartFile(testFile);

        return testRecipePictureAddDTO;
    }

    @Test
    void test_findById_nonExisting(){
                //Act
        Optional<Recipe> byNonExistingID = serviceToTest.findById(8888L);

        //Assert
        assertTrue(byNonExistingID.isEmpty());

    }

    @Test
    void test_findById_existing(){
        //Arrange
        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
        Recipe testRecipe = createTestRecipe(
                "recipeName",
                LocalDateTime.of(2023, Month.NOVEMBER, 3, 6, 30, 40, 50000),
                testCategorySM,
                testUser);
        when(mockedRecipeRepository.findById(testRecipe.getId()))
                .thenReturn(Optional.of(testRecipe));

        //Act
        Optional<Recipe> byExistingID = serviceToTest.findById(testRecipe.getId());
        //Assert
        assertTrue(byExistingID.isPresent());
        assertEquals(testRecipe.getName(), byExistingID.get().getName());
    }

    private RecipeViewDTO createTestRecipeViewDTObefore(User testUser) {

        RecipeViewDTO testRecipeViewDTO = new RecipeViewDTO();
        testRecipeViewDTO
                .setName("recipeName")
                .setServings((short) 2)
                .setRecipeCategories(Set.of(RecipeCategoryEnum.SAUCE, RecipeCategoryEnum.APPETIZER))
                .setRecipeSource("recSource")
                .setPublishedBy(testUser.getDisplayNickname())
                .setCookSteps("cook cook cook");

        return testRecipeViewDTO;
    }

    private RecipeViewDTO createTestRecipeViewDTO(String recipeName, String date, User testUser) {

        RecipeViewDTO testRecipeViewDTO = new RecipeViewDTO();
        testRecipeViewDTO
                .setName(recipeName)
                .setServings((short) 2)
                .setDateLastModified(date)
                .setRecipeCategories(Set.of(RecipeCategoryEnum.SAUCE, RecipeCategoryEnum.APPETIZER))
                .setRecipeSource("recSource")
                .setCookTime("30min")
                .setPrepTime("1h")
                .setPublishedBy(testUser.getDisplayNickname())
                .setCookSteps("cook cook cook")
                .setRecipeIngredientWithDetailsDTOSet(createRecipeIngredientsWithDetailsDTOSet());

        return testRecipeViewDTO;
    }

    private Set<RecipeIngredientWithDetailsDTO> createRecipeIngredientsWithDetailsDTOSet() {

        Set<RecipeIngredientWithDetailsDTO> testRecipeIngredientsWithDetailsDTOSet = new LinkedHashSet<>();

        RecipeIngredientWithDetailsDTO testRIDDTO1 = new RecipeIngredientWithDetailsDTO();
        testRIDDTO1.setIngredientName("fish")
                .setQty("6.70")
                .setMeasureBG("ч.л.");

        testRecipeIngredientsWithDetailsDTOSet.add(testRIDDTO1);

        RecipeIngredientWithDetailsDTO testRIDDTO2 = new RecipeIngredientWithDetailsDTO();
        testRIDDTO2.setIngredientName("oil")
                .setQty("6.70")
                .setMeasureBG("ч.л.");

        testRecipeIngredientsWithDetailsDTOSet.add(testRIDDTO2);

        return testRecipeIngredientsWithDetailsDTOSet;
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
}
