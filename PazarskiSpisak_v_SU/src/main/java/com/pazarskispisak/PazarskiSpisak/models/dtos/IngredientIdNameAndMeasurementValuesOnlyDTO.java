package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class IngredientIdNameAndMeasurementValuesOnlyDTO implements Serializable {

    private Long id;
    private String name;
//    private Set<String> acceptableMeasurementUnitsForRecipeDescription;
    private Map<IngredientMeasurementUnitEnum, String> acceptableMeasurementUnitsForRecipeDescriptionMap;

   public IngredientIdNameAndMeasurementValuesOnlyDTO(){
       this.acceptableMeasurementUnitsForRecipeDescriptionMap = new LinkedHashMap<>();
   }

   public IngredientIdNameAndMeasurementValuesOnlyDTO (Ingredient ingredient){
       this();
       this.setId(ingredient.getId());
       this.setName(ingredient.getName());
       this.acceptableMeasurementUnitsForRecipeDescriptionMap.put(ingredient.getMainUnitOfMeasurement(), ingredient.getMainUnitOfMeasurement().getMeasureBG());
       ingredient.getIngredientAltMUVMap().keySet()
               .forEach(key -> {
                   acceptableMeasurementUnitsForRecipeDescriptionMap.put(key, key.getMeasureBG());
               });
//
//
//       this.acceptableMeasurementUnitsForRecipeDescription.addAll(ingredient.getIngredientAltMUVMap().keySet()
//               .stream()
//               .map(mu -> mu.getMeasureBG())
//               .collect(Collectors.toSet()));
   }

    public Long getId() {
        return id;
    }

    public IngredientIdNameAndMeasurementValuesOnlyDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public IngredientIdNameAndMeasurementValuesOnlyDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Map<IngredientMeasurementUnitEnum, String> getAcceptableMeasurementUnitsForRecipeDescriptionMap() {
        return acceptableMeasurementUnitsForRecipeDescriptionMap;
    }

    public IngredientIdNameAndMeasurementValuesOnlyDTO setAcceptableMeasurementUnitsForRecipeDescriptionMap(Map<IngredientMeasurementUnitEnum, String> acceptableMeasurementUnitsForRecipeDescriptionMap) {
        this.acceptableMeasurementUnitsForRecipeDescriptionMap = acceptableMeasurementUnitsForRecipeDescriptionMap;
        return this;
    }

    @Override
    public String toString() {
        return "IngredientIdNameAndMeasurementValuesOnlyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", acceptableMeasurementUnitsForRecipeDescriptionMap=" + acceptableMeasurementUnitsForRecipeDescriptionMap +
                '}';
    }
}
