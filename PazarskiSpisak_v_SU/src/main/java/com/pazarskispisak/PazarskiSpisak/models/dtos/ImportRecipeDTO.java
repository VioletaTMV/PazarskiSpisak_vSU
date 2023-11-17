package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pazarskispisak.PazarskiSpisak.models.entities.Recipe;
import com.pazarskispisak.PazarskiSpisak.models.entities.User;

import java.io.Serializable;

public class ImportRecipeDTO implements Serializable {

    @Expose
    @SerializedName("cook_id")
    private Integer legacyCookId;

    @Expose
    @SerializedName("cook_name")
    private String name;

    @Expose
    @SerializedName("cook_picture")
    private String picture;

    @Expose
    @SerializedName("cook_portions")
    private Short servings;

    @Expose
    @SerializedName("cook_description")
    private String cookSteps;

    @Expose
    @SerializedName("cook_notes")
    private String notes;

    @Expose
    @SerializedName("cook_visible")
    private short isVisible;

    @Expose
    @SerializedName("cook_userid")
    private Short cook_userId;

    @Expose
    @SerializedName("cook_addtime")
    private String datePublished;

    @Expose
    @SerializedName("cook_views")
    private Integer viewCount;

    public ImportRecipeDTO() {
    }


    public Integer getLegacyCookId() {
        return legacyCookId;
    }

    public void setLegacyCookId(Integer legacyCookId) {
        this.legacyCookId = legacyCookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getServings() {
        return servings;
    }

    public void setServings(Short servings) {
        this.servings = servings;
    }

    public String getCookSteps() {
        return cookSteps;
    }

    public void setCookSteps(String cookSteps) {
        this.cookSteps = cookSteps;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public short isVisible() {
        return isVisible;
    }

    public void setVisible(short visible) {
        isVisible = visible;
    }

    public Short getCook_userId() {
        return cook_userId;
    }

    public void setCook_userId(Short cook_userId) {
        this.cook_userId = cook_userId;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
