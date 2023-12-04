package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.repository.IngredientRepository;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import com.pazarskispisak.PazarskiSpisak.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        IngredientDTO testIngredientDTO = createTestIngredientDTO();

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
    void findIngredientIdNameAndMeasuresById() {
    }

    @Test
    void getAllowedMeasurementUnitEnumsForProduct() {
    }

    @Test
    void addAllowedMeasurementUnitsForEachProductSelectedToBindToItsOptionsOnReload() {
    }

    @Test
    void ingredientExists() {
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
    private IngredientDTO createTestIngredientDTO() {

        IngredientDTO testIngredientDTO = new IngredientDTO()
                .setName("ingrName")
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