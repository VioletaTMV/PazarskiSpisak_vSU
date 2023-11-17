package com.pazarskispisak.PazarskiSpisak.service;

import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientIdNameAndMeasurementValuesOnlyDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeIngredientWithDetailsAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IngredientService{

    boolean areImported();

    void save(Ingredient ingredient);

    void saveAll(List<Ingredient> ingredientsToSaveToDB);

    Optional<Ingredient> findByLegacyArticleId(int legacyArticleId);

    IngredientMeasurementUnitEnum findMainUnitOfMeasurement(Ingredient ingredient);

    List<IngredientDTO> getAllIngredientsSortedAlphabetically();

    List<IngredientMeasurementUnitEnum> getOnlyMainUnitsOfMeasureForIngredients();

    List<IngredientMeasurementUnitEnum> getOnlyQuantifiableAlternativeUnitsOfMeasureForIngredients();

    Map<IngredientMeasurementUnitEnum, Float> populateIngredientDTOAltUnitsOfMeasureAndValueWithData();

    boolean saveNewIngredient(IngredientDTO ingredientDTO);

    IngredientIdNameAndMeasurementValuesOnlyDTO findIngredientIdNameAndMeasuresById(Long id);

    Map<IngredientMeasurementUnitEnum, String> getAllowedMeasurementUnitEnumsForProduct(Long ingredientId);

    void addAllowedMeasurementUnitsForEachProductSelectedToBindToItsOptionsOnReload(List<RecipeIngredientWithDetailsAddDTO> recipeIngredientWithDetailsAddDTOList);

    boolean ingredientExists(Long ingredientId);

    Optional <Ingredient> findById(Long value);
}
