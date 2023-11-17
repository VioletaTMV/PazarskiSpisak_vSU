//package com.pazarskispisak.PazarskiSpisak.models.entities;
//
//import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
//import jakarta.persistence.*;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//@Embeddable
//public class IngredientMeasuresValuesID implements Serializable {
//
//    @Column(name = "ingredient_id")
//    private Long ingredientId;
//
//    @Enumerated(EnumType.STRING)
////    @Column(name = "alt_measurement_unit")
//    private IngredientMeasurementUnitEnum alternativeMeasurementUnit;
//
//    public IngredientMeasuresValuesID(){}
//
//    public IngredientMeasuresValuesID(Long ingredientId, IngredientMeasurementUnitEnum alternativeMeasurementUnit) {
//        this.ingredientId = ingredientId;
//        this.alternativeMeasurementUnit = alternativeMeasurementUnit;
//    }
//
//    public Long getIngredientId() {
//        return ingredientId;
//    }
//
//    public IngredientMeasuresValuesID setIngredientId(Long ingredientId) {
//        this.ingredientId = ingredientId;
//        return this;
//    }
//
//    public IngredientMeasurementUnitEnum getAlternativeMeasurementUnit() {
//        return alternativeMeasurementUnit;
//    }
//
//    public IngredientMeasuresValuesID setAlternativeMeasurementUnit(IngredientMeasurementUnitEnum alternativeMeasurementUnit) {
//        this.alternativeMeasurementUnit = alternativeMeasurementUnit;
//        return this;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        IngredientMeasuresValuesID that = (IngredientMeasuresValuesID) o;
//        return Objects.equals(getIngredientId(), that.getIngredientId()) && getAlternativeMeasurementUnit() == that.getAlternativeMeasurementUnit();
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getIngredientId(), getAlternativeMeasurementUnit());
//    }
//
////        @ManyToOne
////    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
////    private Ingredient ingredient;
////
////    @Enumerated(EnumType.STRING)
////    @Column(name = "alt_measurement_unit")
////    private IngredientMeasurementUnitEnum alternativeMeasurementUnit;
////
////    public IngredientMeasuresValuesID(){}
////
////    public IngredientMeasuresValuesID(Ingredient ingredient, IngredientMeasurementUnitEnum alternativeMeasurementUnit) {
////        this.ingredient = ingredient;
////        this.alternativeMeasurementUnit = alternativeMeasurementUnit;
////    }
////
////    public Ingredient getIngredient() {
////        return ingredient;
////    }
////
////    public IngredientMeasurementUnitEnum getAlternativeMeasurementUnit() {
////        return alternativeMeasurementUnit;
////    }
////
////    @Override
////    public boolean equals(Object o) {
////        if (this == o) return true;
////        if (o == null || getClass() != o.getClass()) return false;
////        IngredientMeasuresValuesID that = (IngredientMeasuresValuesID) o;
////        return Objects.equals(getIngredient(), that.getIngredient()) && getAlternativeMeasurementUnit() == that.getAlternativeMeasurementUnit();
////    }
////
////    @Override
////    public int hashCode() {
////        return Objects.hash(getIngredient(), getAlternativeMeasurementUnit());
////    }
//}
//
//
