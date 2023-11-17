//package com.pazarskispisak.PazarskiSpisak.models.entities;
//
//import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "ingredient_measures_values")
//public class IngredientMeasuresValues {
//
//    @EmbeddedId
//    private IngredientMeasuresValuesID ingredientMeasuresValuesID;
//
//    @ManyToOne(targetEntity = Ingredient.class, fetch = FetchType.LAZY)
////    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
//    @MapsId("ingredientId")
//    private Ingredient ingredient;
//
////    @Enumerated(EnumType.STRING)
////    @JoinColumn(name = "alt_measurement_unit", insertable = false, updatable = false)
////    @MapsId("alternativeMeasurementUnit")
////    private IngredientMeasurementUnitEnum alternativeMeasurementUnit;
//
//    @Column(name = "alt_measure_value", nullable = false)
//    private Float alternativeMeasureValue;
//
//    public IngredientMeasuresValues() {
//    }
//
//    public IngredientMeasuresValues(Ingredient ingredient, Float value) {
//        this.ingredient = ingredient;
//        this.alternativeMeasureValue = value;
//    }
//
//    public IngredientMeasuresValuesID getIngredientMeasuresValuesID() {
//        return ingredientMeasuresValuesID;
//    }
//
//    public void setIngredientMeasuresValuesID(IngredientMeasuresValuesID ingredientMeasuresValuesID) {
//        this.ingredientMeasuresValuesID = ingredientMeasuresValuesID;
//    }
//
//    public Float getAlternativeMeasureValue() {
//        return alternativeMeasureValue;
//    }
//
//    public void setAlternativeMeasureValue(Float alternativeMeasureValue) {
//        this.alternativeMeasureValue = alternativeMeasureValue;
//    }
//
//    public Ingredient getIngredient() {
//        return ingredient;
//    }
//
//    public IngredientMeasuresValues setIngredient(Ingredient ingredient) {
//        this.ingredient = ingredient;
//        return this;
//    }
//
//}
