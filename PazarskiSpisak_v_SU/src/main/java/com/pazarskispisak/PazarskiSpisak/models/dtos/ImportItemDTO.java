package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImportItemDTO {

   @Expose
   @SerializedName("article_id")
    private Short legacyArticleId;

    @Expose
    @SerializedName("article_name")
    private String name;

    @Expose
    @SerializedName("article_category")
    private Short legacyArtcategory;

    public ImportItemDTO(){}

    public ImportItemDTO(Short legacyArticleId, String name, Short legacyArtcategory) {
        this.legacyArticleId = legacyArticleId;
        this.name = name;
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

    public Short getLegacyArtcategory() {
        return legacyArtcategory;
    }

    public void setLegacyArtcategory(Short legacyArtcategory) {
        this.legacyArtcategory = legacyArtcategory;
    }
}
