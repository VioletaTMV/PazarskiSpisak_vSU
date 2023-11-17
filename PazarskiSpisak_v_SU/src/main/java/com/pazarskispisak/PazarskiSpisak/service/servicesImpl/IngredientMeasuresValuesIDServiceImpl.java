//package com.pazarskispisak.PazarskiSpisak.services.servicesImpl;
//
//import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
//import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnits;
//import com.pazarskispisak.PazarskiSpisak.repositories.IngredientMeasuresValuesIDRepository;
//import com.pazarskispisak.PazarskiSpisak.services.IngredientMeasuresValuesIDService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class IngredientMeasuresValuesIDServiceImpl implements IngredientMeasuresValuesIDService {
//
//    private IngredientMeasuresValuesIDRepository ingredientMeasuresValuesIDRepository;
//
//    @Autowired
//    public IngredientMeasuresValuesIDServiceImpl(IngredientMeasuresValuesIDRepository ingredientMeasuresValuesIDRepository) {
//        this.ingredientMeasuresValuesIDRepository = ingredientMeasuresValuesIDRepository;
//    }
//
//    @Override
//    public List<IngredientMeasurementUnits> findAlternativeMeasurementUnitsForIngredient(Ingredient ingredient) {
//        return this.ingredientMeasuresValuesIDRepository.findAlternativeMeasurementUnitByIngredient(ingredient);
//    }
//}
