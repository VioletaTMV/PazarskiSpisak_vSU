package com.pazarskispisak.PazarskiSpisak.repository;

import com.pazarskispisak.PazarskiSpisak.models.entities.ItemCategorySupermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategorySupermarket, Long> {
    ItemCategorySupermarket findBylegacyArtcategoryId(Short legacyArtcategory);

    List<ItemCategorySupermarket> findByIsFoodTrueOrderByNameAsc();

    Optional<ItemCategorySupermarket> findByName(String value);

//    List<ItemCategorySupermarket> findByIsFoodFalseOrderByNameAsc();

    List<ItemCategorySupermarket> findByIsFoodFalseOrderByOrderInShoppingListAsc();

    Optional<ItemCategorySupermarket> findFirstByOrderByOrderInShoppingListDesc();


}
