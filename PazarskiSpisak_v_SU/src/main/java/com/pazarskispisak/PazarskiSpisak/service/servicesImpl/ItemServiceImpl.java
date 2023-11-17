package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.ItemDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.Item;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.repository.ItemRepository;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import com.pazarskispisak.PazarskiSpisak.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private ModelMapper modelMapper;
    private ItemCategoryService itemCategoryService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper, ItemCategoryService itemCategoryService) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.itemCategoryService = itemCategoryService;
    }

    @Override
    public void saveAll(List<Item> itemsToSaveToDB) {
        this.itemRepository.saveAll(itemsToSaveToDB);
    }

    @Override
    public Optional<Item> findByName(String name) {
        return this.itemRepository.findByName(name);
    }

    @Override
    public boolean saveNewItem(ItemDTO itemDTO) {

        Item newItem = this.modelMapper.map(itemDTO, Item.class);

        newItem.setItemCategory(this.itemCategoryService.findByName(newItem.getItemCategory().getName()).get());

        try {
            this.itemRepository.save(newItem);
            return true;
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }




}
