package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImportMainCourseCategoryRecipesDTO implements Serializable {

    @Expose
    @SerializedName("cook_id")
    private Integer legacyCookIdForRecipeInMainCourseCategory;

    public ImportMainCourseCategoryRecipesDTO(){}

    public Integer getLegacyCookIdForRecipeInMainCourseCategory() {
        return legacyCookIdForRecipeInMainCourseCategory;
    }
}
