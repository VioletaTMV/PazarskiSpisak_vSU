package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.UserBasicDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.*;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.UserRoleEnum;
import com.pazarskispisak.PazarskiSpisak.repository.*;
import com.pazarskispisak.PazarskiSpisak.service.ShoppingListFromRecipesService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

//        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
//        Ingredient testIngredient1 = createTestIngredient("fish", testCategorySM);
//        Ingredient testIngredient2 = createTestIngredient("oil", testCategorySM);
        UserBasicDTO testUserBasicDTO = createTestUserBasicDTO(1L, "testDisplayName");
        UserRoleEntity userRoleUser = createUserRoleEntityUser();
        User testUser = createTestUser("test@t.com", "testDisplayName", userRoleUser);
//        ShoppingListFromRecipes testShopListWithUserOnly = createTestShopListWithUserOnly(testUser);

        shopListServiceToTest.createNewShopListForUser(testUserBasicDTO);

        User user = userService.findByDisplayNickname(testUserBasicDTO.getDisplayNickname()).get();
        ShoppingListFromRecipes shopLR = user.getShoppingListFromRecipes();

        Assertions.assertNotNull(shopLR);
        assertEquals(user.getDisplayNickname(),shopLR.getCooker().getDisplayNickname());
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
