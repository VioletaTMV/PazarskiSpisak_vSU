package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.ItemDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.Item;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.repository.ItemRepository;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    private ItemServiceImpl serviceToTest;
    @Mock
    private ItemRepository mockedItemRepository;
    @Mock
    private ModelMapper mockedModelMapper;
    @Mock
    private ItemCategoryService mockedItemCategoryService;

    @BeforeEach
    void setUp() {
        serviceToTest = new ItemServiceImpl(mockedItemRepository, mockedModelMapper, mockedItemCategoryService);
    }

    @Test
    void test_saveNewItem() {
        //Arrange
        ItemDTO testItemDTO = createTestItemDTO();
        Item testItem = createTestItem();
        when(mockedModelMapper.map(testItemDTO, Item.class))
                .thenReturn(testItem);
        when(mockedItemRepository.save(testItem))
                .thenReturn(testItem);

        //Act
        Item mappedItem = mockedModelMapper.map(testItemDTO, Item.class);
  //      boolean saved = serviceToTest.saveNewItem(testItemDTO);
        Item savedItem = mockedItemRepository.save(mappedItem);

        //Assert
        assertEquals(mappedItem.getName(), testItemDTO.getName());
        assertEquals(mappedItem.getItemCategory().getName(), testItemDTO.getItemCategorySupermarketName());
        assertEquals(testItem, savedItem);


    }

    private static Item createTestItem() {

        Item testItem = new Item();
        testItem.setName("item1");
        testItem.setItemCategory(createTestItemCatSupermarket());

        return testItem;
    }


    private static ItemDTO createTestItemDTO() {

        ItemDTO testItemDTO = new ItemDTO();
        testItemDTO.setName("item1");
        testItemDTO.setItemCategorySupermarketName(createTestItemCatSupermarket().getName());

        return testItemDTO;
    }

    private static ItemCategorySupermarket createTestItemCatSupermarket() {

        ItemCategorySupermarket testItemCategorySupermarket = new ItemCategorySupermarket();
        testItemCategorySupermarket.setName("cat1");
        testItemCategorySupermarket.setFood(false);
        testItemCategorySupermarket.setOrderInShoppingList((short) 1);

        return testItemCategorySupermarket;
    }
}