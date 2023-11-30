package com.pazarskispisak.PazarskiSpisak.models.entities;

import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@DiscriminatorValue(value = "ingredient")
public class Ingredient extends Item {

    private final static String itemType = "INGREDIENT";

    @Enumerated(EnumType.STRING)
    @Column(name = "main_unit_of_measure" /*, nullable = false*/)
    private IngredientMeasurementUnitEnum mainUnitOfMeasurement;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ingredient_measures_values",
            joinColumns = {@JoinColumn(name = "ingredient_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "alt_measure_unit")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "alt_measure_value")
    private Map<IngredientMeasurementUnitEnum, Float> ingredientAltMUVMap;

    @Enumerated(EnumType.STRING)
    @Column(name = "shopping_list_display_measure_unit" /*, nullable = false*/)
    private IngredientMeasurementUnitEnum shoppingListDisplayUnitOfMeasurement;


    public Ingredient() {
        this.ingredientAltMUVMap = new HashMap<>();
    }


    public void addAlternativeMeasurementUnitAndValueToMap(IngredientMeasurementUnitEnum altMeasureUnit, Float value){
        this.ingredientAltMUVMap.put(altMeasureUnit, value);
    }

    public IngredientMeasurementUnitEnum getMainUnitOfMeasurement() {
        return mainUnitOfMeasurement;
    }

    public void setMainUnitOfMeasurement(IngredientMeasurementUnitEnum mainUnitOfMeasurement) {
        this.mainUnitOfMeasurement = mainUnitOfMeasurement;
    }

    public IngredientMeasurementUnitEnum getShoppingListDisplayUnitOfMeasurement() {
        return shoppingListDisplayUnitOfMeasurement;
    }

    public void setShoppingListDisplayUnitOfMeasurement(IngredientMeasurementUnitEnum shoppingListDisplayUnitOfMeasurement) {
        this.shoppingListDisplayUnitOfMeasurement = shoppingListDisplayUnitOfMeasurement;
    }

    public void setDefaultShoppingListDisplayUnitOfMeasurement() {
        this.shoppingListDisplayUnitOfMeasurement = this.mainUnitOfMeasurement;
    }

    public Map<IngredientMeasurementUnitEnum, Float> getIngredientAltMUVMap() {
        return ingredientAltMUVMap;
    }

    public Ingredient setIngredientAltMUVMap(Map<IngredientMeasurementUnitEnum, Float> ingredientAltMUVMap) {
        this.ingredientAltMUVMap = ingredientAltMUVMap;
        return this;
    }


    @Override
    public String toString() {
        return super.toString() +
                "Ingredient{" +
                "mainUnitOfMeasurement=" + mainUnitOfMeasurement +
                ", ingredientAltMUVMap=" + ingredientAltMUVMap +
                ", shoppingListDisplayUnitOfMeasurement=" + shoppingListDisplayUnitOfMeasurement +
                "} ";
    }
}
