package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.entities.RecipeIngredientWithDetails;
import com.pazarskispisak.PazarskiSpisak.repository.RecipeIngredientsRepository;
import com.pazarskispisak.PazarskiSpisak.service.RecipeIngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientsServiceImpl implements RecipeIngredientsService {

    private RecipeIngredientsRepository recipeIngredientsRepository;

    @Autowired
    public RecipeIngredientsServiceImpl(RecipeIngredientsRepository recipeIngredientsRepository) {
        this.recipeIngredientsRepository = recipeIngredientsRepository;
    }


    @Override
    public boolean areImported() {
        return this.recipeIngredientsRepository.count() > 0;
    }

    @Override
    public void saveAll(List<RecipeIngredientWithDetails> recipeIngredientWithDetailsToSaveToDB) {
        this.recipeIngredientsRepository.saveAll(recipeIngredientWithDetailsToSaveToDB);
    }
}
