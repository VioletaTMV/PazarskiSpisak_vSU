//package com.pazarskispisak.PazarskiSpisak.models.validation;
//
//import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//import java.util.Map;
//
//public class MapValueFloatPositiveOrNullValidator implements ConstraintValidator<MapValueFloatPositiveOrNull, Map<IngredientMeasurementUnitEnum, Float>> {
//
//
//    @Override
//    public boolean isValid(Map<IngredientMeasurementUnitEnum, Float> value, ConstraintValidatorContext context) {
//
//        boolean mapValueIsPositiveOrNull = true;
//
//        for (Float aFloat : value.values()) {
//
//            if (aFloat == null){
//                continue;
//            } else if (aFloat <= 0 || !isNumberOfDecimalNumbersTwoOrLess(aFloat)){
//                mapValueIsPositiveOrNull = false;
//                return false;
//            }
//        }
//
//        return mapValueIsPositiveOrNull;
//    }
//
//    private boolean isNumberOfDecimalNumbersTwoOrLess(Float aFloat) {
//
//        String floatAsString = aFloat.toString();
//
//        if (floatAsString.contains(".")){
//
//            int i = floatAsString.indexOf(".");
//            if (floatAsString.substring(i).length() == 1){
//                return true;
//            } else if (floatAsString.substring(i+1).length() > 2){
//                return false;
//            }
//        }
//
//        return true;
//    }
//}
