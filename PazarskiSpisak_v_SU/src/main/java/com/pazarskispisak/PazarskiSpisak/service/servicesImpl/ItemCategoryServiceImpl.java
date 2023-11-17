package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.ItemCategorySupermarketAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.repository.ItemCategoryRepository;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private ItemCategoryRepository itemCategoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ItemCategoryServiceImpl(ItemCategoryRepository itemCategoryRepository, ModelMapper modelMapper) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.itemCategoryRepository.count() > 0;
    }

    @Override
    public void saveAll(List<ItemCategorySupermarket> itemCategoriesToSaveToDB) {
        this.itemCategoryRepository.saveAll(itemCategoriesToSaveToDB);
    }

    @Override
    public ItemCategorySupermarket getCurrentItemCategoryBasedOnLegacy(Short legacyArtcategory) {
        return this.itemCategoryRepository.findBylegacyArtcategoryId(legacyArtcategory);
    }

    @Override
    public List<ItemCategorySupermarket> getIngredientCategoriesSortedAlphabetically() {

        return this.itemCategoryRepository.findByIsFoodTrueOrderByNameAsc();
    }

    @Override
    public Optional<ItemCategorySupermarket> findByName(String value) {

        return this.itemCategoryRepository.findByName(value);

    }

    @Override
    public List<ItemCategorySupermarket> getNonFoodItemCategoriesOrderedByOrderInShoppingList() {

        return this.itemCategoryRepository.findByIsFoodFalseOrderByOrderInShoppingListAsc();
    }

    @Override
    public boolean saveNewItemCategorySupermarket(ItemCategorySupermarketAddDTO itemCategorySupermarketAddDTO) {

        ItemCategorySupermarket newItemCategorySupermarket = this.modelMapper.map(itemCategorySupermarketAddDTO, ItemCategorySupermarket.class);

        newItemCategorySupermarket.setOrderInShoppingList((short) (getLastNumberFromOrderInShoppingList() + 1));

        try {
            this.itemCategoryRepository.save(newItemCategorySupermarket);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    private short getLastNumberFromOrderInShoppingList() {

        Optional<ItemCategorySupermarket> lastProdCategoryNameOpt = this.itemCategoryRepository.findFirstByOrderByOrderInShoppingListDesc();

        Short lastNumber = lastProdCategoryNameOpt.isPresent() ?
                lastProdCategoryNameOpt.get().getOrderInShoppingList() :
                0;

        return lastNumber;
    }


}
