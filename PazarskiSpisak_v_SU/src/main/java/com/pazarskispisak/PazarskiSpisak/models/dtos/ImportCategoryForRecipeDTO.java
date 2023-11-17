package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImportCategoryForRecipeDTO implements Serializable {

    @Expose
    @SerializedName("cook_id")
    private Integer legacyCookIdForRecipeInCategory;

    public ImportCategoryForRecipeDTO() {
    }

    public Integer getLegacyCookIdForRecipeInCategory() {
        return legacyCookIdForRecipeInCategory;
    }
}
