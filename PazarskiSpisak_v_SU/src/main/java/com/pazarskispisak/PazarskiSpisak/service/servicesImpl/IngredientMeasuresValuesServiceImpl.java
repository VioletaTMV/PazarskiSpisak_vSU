//package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;
//
//import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
//import com.pazarskispisak.PazarskiSpisak.models.entities.IngredientMeasuresValues;
//import com.pazarskispisak.PazarskiSpisak.repository.IngredientMeasuresValuesRepository;
//import com.pazarskispisak.PazarskiSpisak.service.IngredientMeasuresValuesService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class IngredientMeasuresValuesServiceImpl implements IngredientMeasuresValuesService {
//
//    private IngredientMeasuresValuesRepository ingredientMeasuresValuesRepository;
//
//    @Autowired
//    public IngredientMeasuresValuesServiceImpl(IngredientMeasuresValuesRepository ingredientMeasuresValuesRepository) {
//        this.ingredientMeasuresValuesRepository = ingredientMeasuresValuesRepository;
//    }
//
//    @Override
//    public void saveAll(List<IngredientMeasuresValues> ingredientMeasuresValuesToSaveToDB) {
//        this.ingredientMeasuresValuesRepository.saveAll(ingredientMeasuresValuesToSaveToDB);
//    }
//
//    @Override
//    public boolean areImported() {
//        return this.ingredientMeasuresValuesRepository.count() > 0;
//    }
////
////    @Override
////    public List<IngredientMeasurementUnits> findAlternativeMeasurementUnitByIngredientMeasuresValuesIDWhereIngredientMeasuresValuesIDContainsIngredient(Ingredient ingredient) {
////        return this.ingredientMeasuresValuesRepository.findAlternativeMeasurementUnitByIngredientMeasuresValuesIDIngredient(ingredient);
////    }
//
//    @Override
//    public List<IngredientMeasuresValues> findByIngredientMeasuresValuesIDIngredient(Ingredient ingredient) {
//        return this.ingredientMeasuresValuesRepository.findByIngredientMeasuresValuesIDIngredientId(ingredient.getId());
//    }
//
//
//}
