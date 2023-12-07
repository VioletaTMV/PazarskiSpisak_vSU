package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientIdNameAndMeasurementValuesOnlyDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.repository.IngredientRepository;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import com.pazarskispisak.PazarskiSpisak.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    private IngredientService serviceToTest;
    @Mock
    private IngredientRepository mockedIngredientRepository;
    @Mock
    private ModelMapper mockedModelMapper;
    @Mock
    private ItemService mockedItemService;
    @Mock
    private ItemCategoryService mockedItemCategoryService;

    @BeforeEach
    void setUp() {
        serviceToTest = new IngredientServiceImpl(
                mockedIngredientRepository,
                mockedModelMapper,
                mockedItemService,
                mockedItemCategoryService);
    }


    @Test
    void getOnlyMainUnitsOfMeasureForIngredients() {
        //Arrange
        IngredientMeasurementUnitEnum testMainIngredientMUEnum1 = IngredientMeasurementUnitEnum.GRAM;
        IngredientMeasurementUnitEnum testMainIngredientMUEnum2 = IngredientMeasurementUnitEnum.MILLILITER;

        //Act
        List<IngredientMeasurementUnitEnum> testOnlyMainUMForIngredients = serviceToTest.getOnlyMainUnitsOfMeasureForIngredients();

        //Assert
        assertEquals(2, testOnlyMainUMForIngredients.size());
        assertTrue(testOnlyMainUMForIngredients.contains(testMainIngredientMUEnum1));
        assertTrue(testOnlyMainUMForIngredients.contains(testMainIngredientMUEnum2));
    }

    @Test
    void getOnlyQuantifiableAlternativeUnitsOfMeasureForIngredients() {
        //Act
        List<IngredientMeasurementUnitEnum> quantifiableAlt = serviceToTest.getOnlyQuantifiableAlternativeUnitsOfMeasureForIngredients();
        //Assert
        assertFalse(quantifiableAlt.contains(IngredientMeasurementUnitEnum.GRAM));
        assertFalse(quantifiableAlt.contains(IngredientMeasurementUnitEnum.MILLILITER));
        assertFalse(quantifiableAlt.contains(IngredientMeasurementUnitEnum.EMPTY));
        assertFalse(quantifiableAlt.contains(IngredientMeasurementUnitEnum.AT_TASTE));
    }

    @Test
    void populateIngredientDTOAltUnitsOfMeasureAndValueWithData() {
        //Act
        Map<IngredientMeasurementUnitEnum, Float> ingrAltQuantifiableMUMap = serviceToTest.populateIngredientDTOAltUnitsOfMeasureAndValueWithData();

        //Assert
        assertFalse(ingrAltQuantifiableMUMap.isEmpty());
        assertFalse(ingrAltQuantifiableMUMap.containsKey(IngredientMeasurementUnitEnum.GRAM));
        assertFalse(ingrAltQuantifiableMUMap.containsKey(IngredientMeasurementUnitEnum.MILLILITER));
        assertFalse(ingrAltQuantifiableMUMap.containsKey(IngredientMeasurementUnitEnum.EMPTY));
        assertFalse(ingrAltQuantifiableMUMap.containsKey(IngredientMeasurementUnitEnum.AT_TASTE));
        assertTrue(ingrAltQuantifiableMUMap.containsKey(IngredientMeasurementUnitEnum.PIECE));
        assertTrue(ingrAltQuantifiableMUMap.containsKey(IngredientMeasurementUnitEnum.SLICE));
        assertTrue(ingrAltQuantifiableMUMap.containsKey(IngredientMeasurementUnitEnum.TABLE_SPOON));
        assertTrue(ingrAltQuantifiableMUMap.containsKey(IngredientMeasurementUnitEnum.CHUNK));
        assertTrue(ingrAltQuantifiableMUMap.containsKey(IngredientMeasurementUnitEnum.CLOVE));
        assertTrue(ingrAltQuantifiableMUMap.containsKey(IngredientMeasurementUnitEnum.GRAIN));
        assertEquals(13, ingrAltQuantifiableMUMap.size());
    }

    @Test
    void saveNewIngredient() {
        //Arrange
        Ingredient testIngredient = createTestIngredient();
        Ingredient testIngredientBeforeAssignmentOfItemCategory = createTestIngredientWithoutItemCategory();
        IngredientDTO testIngredientDTO = createTestIngredientDTO("ingrName");

        when(mockedModelMapper.map(testIngredientDTO, Ingredient.class))
                .thenReturn(testIngredientBeforeAssignmentOfItemCategory);
        when(mockedIngredientRepository.save(testIngredient))
                .thenReturn(testIngredient);
        //Act
        Ingredient mappedTestIngredient = mockedModelMapper.map(testIngredientDTO, Ingredient.class);
        boolean b = serviceToTest.saveNewIngredient(testIngredientDTO);

        //Assert
        assertEquals(testIngredientBeforeAssignmentOfItemCategory, mappedTestIngredient);

    }

    @Test
    void test_getAllIngredientsSortedAlphabetically() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        Ingredient testIngredient1 = createTestIngredient("ingrName1", testCategorySM);
        Ingredient testIngredient2 = createTestIngredient("ingrName2", testCategorySM);
        Ingredient testIngredient3 = createTestIngredient("ingrName3", testCategorySM);
        List<Ingredient> testIngrList = List.of(testIngredient2, testIngredient3, testIngredient1);
        IngredientDTO testIngrDTO1 = createTestIngredientDTO("ingrName1");
        IngredientDTO testIngrDTO2 = createTestIngredientDTO("ingrName2");
        IngredientDTO testIngrDTO3 = createTestIngredientDTO("ingrName3");

       doReturn(testIngrList).when(mockedIngredientRepository).findAll();
       when(mockedModelMapper.map(testIngredient1, IngredientDTO.class))
               .thenReturn(testIngrDTO1);
        when(mockedModelMapper.map(testIngredient2, IngredientDTO.class))
                .thenReturn(testIngrDTO2);
        when(mockedModelMapper.map(testIngredient3, IngredientDTO.class))
                .thenReturn(testIngrDTO3);


        List<IngredientDTO> testResult = serviceToTest.getAllIngredientsSortedAlphabetically();

        Assertions.assertEquals(testIngredient1.getName(), testResult.get(0).getName());
        Assertions.assertEquals(testIngredient2.getName(), testResult.get(1).getName());
        Assertions.assertEquals(testIngredient3.getName(), testResult.get(2).getName());

    }


    @Test
    void findIngredientIdNameAndMeasuresById() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        Ingredient testIngredient1 = createTestIngredient("ingrName1", testCategorySM);

        when(mockedIngredientRepository.findById(1L))
                .thenReturn(Optional.of(testIngredient1));

        IngredientIdNameAndMeasurementValuesOnlyDTO testResult = serviceToTest.findIngredientIdNameAndMeasuresById(1L);

        assertEquals("ingrName1", testResult.getName());
        assertTrue(testResult.getAcceptableMeasurementUnitsForRecipeDescriptionMap().containsKey(IngredientMeasurementUnitEnum.PIECE));
        assertTrue(testResult.getAcceptableMeasurementUnitsForRecipeDescriptionMap().containsKey(IngredientMeasurementUnitEnum.TABLE_SPOON));
        assertTrue(testResult.getAcceptableMeasurementUnitsForRecipeDescriptionMap().containsKey(IngredientMeasurementUnitEnum.GRAM));
      assertEquals(3, testResult.getAcceptableMeasurementUnitsForRecipeDescriptionMap().size());
    }

    @Test
    void findIngredientIdNameAndMeasuresById_whenNonExistingIngredientID() {

        IngredientIdNameAndMeasurementValuesOnlyDTO testResult = serviceToTest.findIngredientIdNameAndMeasuresById(11111L);

        Assertions.assertNull(testResult);
    }

    @Test
    void getAllowedMeasurementUnitEnumsForProduct() {
    }

    @Test
    void addAllowedMeasurementUnitsForEachProductSelectedToBindToItsOptionsOnReload() {
    }

    @Test
    void ingredientExists_whenNonExistingIngredientID() {

        boolean b = serviceToTest.ingredientExists(3333L);

        assertFalse(b);
    }

    @Test
    void ingredientExists_whenNullIngredientID() {

        boolean b = serviceToTest.ingredientExists(null);

        assertFalse(b);
    }

    @Test
    void ingredientExists() {

        ItemCategorySupermarket testCategorySM = createTestCategory("fish and seafood", (short) 1);
        Ingredient testIngredient1 = createTestIngredient("ingrName1", testCategorySM);
        when(mockedIngredientRepository.findById(1L))
                .thenReturn(Optional.of(testIngredient1));

        boolean b = serviceToTest.ingredientExists(1L);

        assertTrue(b);
    }

    private ItemCategorySupermarket createTestCategory(String testItemCategoryName, short orderShopList) {

        ItemCategorySupermarket testItemCategory = new ItemCategorySupermarket();
        testItemCategory.setName(testItemCategoryName);
        testItemCategory.setFood(true);
        testItemCategory.setOrderInShoppingList(orderShopList);

        return testItemCategory;
    }

    private static Ingredient createTestIngredient() {

        Ingredient testIngredient = new Ingredient();

        testIngredient.setName("ingrName");
        testIngredient.setItemCategory(ItemCategoryServiceImplTest.createTestItemCategorySupermarket());
        testIngredient.setMainUnitOfMeasurement(IngredientMeasurementUnitEnum.GRAM);
        testIngredient.setShoppingListDisplayUnitOfMeasurement(IngredientMeasurementUnitEnum.EMPTY);
        testIngredient.setIngredientAltMUVMap(createTestIngredientAltMUVMap());

        return testIngredient;

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

    private IngredientDTO createTestIngredientDTO(String name) {

        IngredientDTO testIngredientDTO = new IngredientDTO()
                .setName(name)
                .setMainUnitOfMeasurement(IngredientMeasurementUnitEnum.GRAM)
                .setShoppingListDisplayUnitOfMeasurement(IngredientMeasurementUnitEnum.EMPTY)
                .setItemCategorySupermarketName("productCategoryName")
                .setIngredientAltMUVMap(createTestIngredientDTOAltMUVMap());

        return testIngredientDTO;
    }

    private static Ingredient createTestIngredientWithoutItemCategory() {

        Ingredient testIngredientWithNoItemCategory = new Ingredient();

        testIngredientWithNoItemCategory.setName("ingrName");
        testIngredientWithNoItemCategory.setItemCategory(null);
        testIngredientWithNoItemCategory.setMainUnitOfMeasurement(IngredientMeasurementUnitEnum.GRAM);
        testIngredientWithNoItemCategory.setShoppingListDisplayUnitOfMeasurement(IngredientMeasurementUnitEnum.EMPTY);
        testIngredientWithNoItemCategory.setIngredientAltMUVMap(createTestIngredientAltMUVMap());

        return testIngredientWithNoItemCategory;

    }

    private static Map<IngredientMeasurementUnitEnum, Float> createTestIngredientAltMUVMap() {

        Map<IngredientMeasurementUnitEnum, Float> testIngredientAltMUVMap = new HashMap<>();
        testIngredientAltMUVMap.put(IngredientMeasurementUnitEnum.PIECE, 2F);
        testIngredientAltMUVMap.put(IngredientMeasurementUnitEnum.TABLE_SPOON, 3F);
        testIngredientAltMUVMap.put(IngredientMeasurementUnitEnum.SLICE, 1F);

        return testIngredientAltMUVMap;
    }

    private static Map<IngredientMeasurementUnitEnum, Float> createTestIngredientDTOAltMUVMap() {

        Map<IngredientMeasurementUnitEnum, Float> testIngredientDTOAltMUVMap = new HashMap<>();
        testIngredientDTOAltMUVMap.put(IngredientMeasurementUnitEnum.PIECE, 2F);
        testIngredientDTOAltMUVMap.put(IngredientMeasurementUnitEnum.TABLE_SPOON, 3F);
        testIngredientDTOAltMUVMap.put(IngredientMeasurementUnitEnum.SLICE, 1F);

        return testIngredientDTOAltMUVMap;
    }
}