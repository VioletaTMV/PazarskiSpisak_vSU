package com.pazarskispisak.PazarskiSpisak.service;

import com.pazarskispisak.PazarskiSpisak.models.dtos.ItemDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.Item;
import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    void saveAll(List<Item> itemsToSaveToDB);

    Optional<Item> findByName(String name);

    boolean saveNewItem(ItemDTO itemDTO);


}
