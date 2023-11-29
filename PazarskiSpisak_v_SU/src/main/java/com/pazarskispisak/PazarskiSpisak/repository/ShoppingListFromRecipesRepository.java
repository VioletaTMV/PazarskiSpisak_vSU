package com.pazarskispisak.PazarskiSpisak.repository;

import com.pazarskispisak.PazarskiSpisak.models.entities.Recipe;
import com.pazarskispisak.PazarskiSpisak.models.entities.ShoppingListFromRecipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingListFromRecipesRepository extends JpaRepository<ShoppingListFromRecipes, Long> {


    Optional<ShoppingListFromRecipes> findByCookerId(Long id);

    Optional <ShoppingListFromRecipes> findByCookerEmail(String userEmail);

    List<ShoppingListFromRecipes> findByLastAccessedDateBefore(LocalDate inactiveShoplistDate);
}
