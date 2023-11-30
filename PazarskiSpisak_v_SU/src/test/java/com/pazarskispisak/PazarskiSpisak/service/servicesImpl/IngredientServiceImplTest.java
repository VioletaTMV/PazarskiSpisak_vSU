package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.repository.IngredientRepository;
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

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    private IngredientServiceImpl serviceToTest;
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
    }

    @Test
    void populateIngredientDTOAltUnitsOfMeasureAndValueWithData() {
    }

    @Test
    void saveNewIngredient() {
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

    private static Ingredient createTestIngredient(){

        Ingredient testIngredient = new Ingredient();

        testIngredient.setName("ingrName");
        testIngredient.setItemCategory(ItemCategoryServiceImplTest.createTestItemCategorySupermarket());
        testIngredient.setMainUnitOfMeasurement(IngredientMeasurementUnitEnum.GRAM);
        testIngredient.setShoppingListDisplayUnitOfMeasurement(IngredientMeasurementUnitEnum.EMPTY);
        testIngredient.setIngredientAltMUVMap(createTestIngredientAltMUVMap());

        return testIngredient;

    }

    private static Map<IngredientMeasurementUnitEnum, Float> createTestIngredientAltMUVMap(){

        Map<IngredientMeasurementUnitEnum, Float> testIngredientAltMUVMap = new HashMap<>();
        testIngredientAltMUVMap.put(IngredientMeasurementUnitEnum.PIECE, 2F);
        testIngredientAltMUVMap.put(IngredientMeasurementUnitEnum.TABLE_SPOON, 3F);
        testIngredientAltMUVMap.put(IngredientMeasurementUnitEnum.SLICE, 1F);

        return testIngredientAltMUVMap;
    }
}