package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.validations.ValidRecipe;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RecipeShortInfoDTO {

    @ValidRecipe
    private Long id;
    private String name;
    private String picture;
    private Short servings;
    @NotNull(message = "Задължително поле.")
    @Min(value = 1, message = "Минимум 1 порция.")
    @Max(value = 99, message = "Броят порции трябва да бъде под 100.")
    private Short desiredServings;


    public RecipeShortInfoDTO(){}

    public Long getId() {
        return id;
    }

    public RecipeShortInfoDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecipeShortInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public RecipeShortInfoDTO setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public Short getServings() {
        return servings;
    }

    public RecipeShortInfoDTO setServings(Short servings) {
        this.servings = servings;
        return this;
    }

    public Short getDesiredServings() {
        return desiredServings;
    }

    public RecipeShortInfoDTO setDesiredServings(Short desiredServings) {
        this.desiredServings = desiredServings;
        return this;
    }

    @Override
    public String toString() {
        return "RecipeShortInfoDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", servings=" + servings +
                ", desiredServings=" + desiredServings +
                '}';
    }
}
