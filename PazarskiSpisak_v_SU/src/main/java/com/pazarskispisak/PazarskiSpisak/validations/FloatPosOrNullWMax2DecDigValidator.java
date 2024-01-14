package com.pazarskispisak.PazarskiSpisak.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FloatPosOrNullWMax2DecDigValidator implements ConstraintValidator<FloatPositiveOrNullWithMax2DecimalDigits, Float> {


    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {

        boolean floatIsPositiveOrNullWithMax2DecimalDigits = true;

        if (value == null){
            return true;
        } else if (value <= 0 || !isNumberOfDecimalNumbersTwoOrLess(value)){
           return false;
        }

        return floatIsPositiveOrNullWithMax2DecimalDigits;

    }

    private boolean isNumberOfDecimalNumbersTwoOrLess(Float aFloat) {

        String floatAsString = aFloat.toString();

        if (floatAsString.contains(".")){

            int i = floatAsString.indexOf(".");
            if (floatAsString.substring(i).length() == 1){
                return true;
            } else if (floatAsString.substring(i+1).length() > 2){
                return false;
            }
        }

        return true;
    }
}
