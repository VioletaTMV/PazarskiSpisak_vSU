package com.pazarskispisak.PazarskiSpisak.repository;

import com.pazarskispisak.PazarskiSpisak.models.entities.Recipe;
import com.pazarskispisak.PazarskiSpisak.models.entities.RecipeCategoryGroup;
import com.pazarskispisak.PazarskiSpisak.models.entities.User;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByLegacyCookId(int legacyCookId);

    @Modifying
    @Transactional
    @Query("update Recipe r set r.viewCount=:viewCountPlusOne where r.id =:id")
    void increaseViewTimesByOne(int viewCountPlusOne, Long id);


    Optional<Recipe> findByNameAndPublishedBy(String recipeName, User user);

    List<Recipe> findByRecipeCategoriesContainingOrderByDateLastModifiedDesc(RecipeCategoryEnum recipeCategoryEnum);

    boolean existsByRecipeCategories(RecipeCategoryEnum recipeCategoryEnum);

//    @Query("select rcg " +
//            "from Recipe r" +
//            "join r.recipeCategories rrc" +
//            "join rrc."+
//            "from RecipeCategoryGroup rc" +
//            "join rc.")
//    List<RecipeCategoryGroup> findByRecipeCategoryHavingAtLeastOneRecipeAssigned();


}

