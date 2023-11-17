package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImportRecipeIngredientsDTO implements Serializable {

    @Expose
    @SerializedName("cookArticle_cookid")
    private int legacyCookId;

    @Expose
    @SerializedName("cookArticle_articleID")
    private int legacyArticleId;

    @Expose
    @SerializedName("cookArticle_value")
    private Double mainGrMlValue;

    @Expose
    @SerializedName("cookArticle_count")
    private Double alternativeMeasureValue;

    @Expose
    @SerializedName("cookArticle_calculated")
    private Double mainMeasureQtyValuePerServing;

    public ImportRecipeIngredientsDTO() {
    }

    public int getLegacyCookId() {
        return legacyCookId;
    }

    public int getLegacyArticleId() {
        return legacyArticleId;
    }

    public Double getMainGrMlValue() {
        return mainGrMlValue;
    }

    public Double getAlternativeMeasureValue() {
        return alternativeMeasureValue;
    }

    public Double getMainMeasureQtyValuePerServing() {
        return mainMeasureQtyValuePerServing;
    }
}
