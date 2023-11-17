package com.pazarskispisak.PazarskiSpisak.models.enums;

public enum IngredientMeasurementUnitEnum {

    GRAM ("гр."),
    MILLILITER ("мл."),
    PIECE ("бр."),
    HEAD ("гл."),
    HANDFUL ("шепи"),
    NEST ("гн."),
    GRAIN ("зрн."),
    CHUNK ("рзн."),
    TABLE_SPOON ("с.л."),
    TEA_SPOON ("ч.л."),
    TEACUP ("ч.ч."),
    SLICE ("фил."),
    PINCH ("щипка"),
    CLOVE ("ск."),
    STALK ("стр."),
    AT_TASTE ("на вкус"),
    EMPTY (null);

    private final String measureBG;

   private IngredientMeasurementUnitEnum(String measureBG) {
       this.measureBG = measureBG;
    }

    public String getMeasureBG(){
        return this.measureBG;
   }

    public static IngredientMeasurementUnitEnum getIngredientMeasurementUnitFromItsValue(String measureBG){

       switch (measureBG){
           case "гр.":
               return GRAM;
           case "мл.":
               return MILLILITER;
           case "бр.":
               return PIECE;
           case "гл.":
               return HEAD;
           case "шепи":
               return HANDFUL;
           case "гн.":
               return NEST;
           case "зрн.":
               return GRAIN;
           case "рзн.":
               return CHUNK;
           case "с.л.":
               return TABLE_SPOON;
           case "ч.л.":
               return TEA_SPOON;
           case "ч.ч.":
               return TEACUP;
           case "фил.":
               return SLICE;
           case "щипка":
               return PINCH;
           case "ск.":
               return CLOVE;
           case "стр.":
               return STALK;
           case "на вкус":
               return AT_TASTE;
       }

       return null;
    }



}

