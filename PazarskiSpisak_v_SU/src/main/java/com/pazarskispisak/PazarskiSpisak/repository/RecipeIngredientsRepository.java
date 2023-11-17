package com.pazarskispisak.PazarskiSpisak.repository;

import com.pazarskispisak.PazarskiSpisak.models.entities.RecipeIngredientWithDetails;
import com.pazarskispisak.PazarskiSpisak.models.entities.RecipeIngredientID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientsRepository extends JpaRepository<RecipeIngredientWithDetails, RecipeIngredientID> {



}
