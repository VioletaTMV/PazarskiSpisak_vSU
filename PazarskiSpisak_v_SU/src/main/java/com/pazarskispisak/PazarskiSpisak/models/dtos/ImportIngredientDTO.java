package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImportIngredientDTO {

    @Expose
    @SerializedName("article_id")
    private Short legacyArticleId;

    @Expose
    @SerializedName("article_name")
    private String name;

    @Expose
    @SerializedName("article_measure_type")
    private Short articleMeasureType;

    @Expose
    @SerializedName("article_measure_alt")
    private String alternativeMeasurementUnit;

    @Expose
    @SerializedName("article_measure_altsize")
    private Float alternativeMeasureValue;

    @Expose
    @SerializedName("article_category")
    private Short legacyArtcategory;

    public ImportIngredientDTO() {
    }

    public ImportIngredientDTO(Short legacyArticleId, String name, Short articleMeasureType, String alternativeMeasurementUnit, Float alternativeMeasureValue, Short legacyArtcategory) {
        this.legacyArticleId = legacyArticleId;
        this.name = name;
        this.articleMeasureType = articleMeasureType;
        this.alternativeMeasurementUnit = alternativeMeasurementUnit;
        this.alternativeMeasureValue = alternativeMeasureValue;
        this.legacyArtcategory = legacyArtcategory;
    }


    public Short getLegacyArticleId() {
        return legacyArticleId;
    }

    public void setLegacyArticleId(Short legacyArticleId) {
        this.legacyArticleId = legacyArticleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getArticleMeasureType() {
        return articleMeasureType;
    }

    public void setArticleMeasureType(Short articleMeasureType) {
        this.articleMeasureType = articleMeasureType;
    }

    public String getAlternativeMeasurementUnit() {
        return alternativeMeasurementUnit;
    }

    public void setAlternativeMeasurementUnit(String alternativeMeasurementUnit) {
        this.alternativeMeasurementUnit = alternativeMeasurementUnit;
    }

    public Float getAlternativeMeasureValue() {
        return alternativeMeasureValue;
    }

    public void setAlternativeMeasureValue(Float alternativeMeasureValue) {
        this.alternativeMeasureValue = alternativeMeasureValue;
    }

    public Short getLegacyArtcategory() {
        return legacyArtcategory;
    }

    public void setLegacyArtcategory(Short legacyArtcategory) {
        this.legacyArtcategory = legacyArtcategory;
    }


}
