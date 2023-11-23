package com.pazarskispisak.PazarskiSpisak.models.dtos;

import com.pazarskispisak.PazarskiSpisak.validations.ValidImageFileFormat;
import com.pazarskispisak.PazarskiSpisak.validations.ValidImageSize;
import org.springframework.web.multipart.MultipartFile;

public class RecipePictureAddDTO {

    private Long recipeId;
    private String recipeName;
    private Long recipePublishedById;
    @ValidImageSize(maxFileSize = 25600, message = "Невалиден размер на файла. Максимум 25КВ.")
    @ValidImageFileFormat(message = "Невалиден файлов формат. Рарешени са PNG, JPG.")
    private MultipartFile multipartFile;

    public RecipePictureAddDTO(){
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public Long getRecipePublishedById() {
        return recipePublishedById;
    }

    public void setRecipePublishedById(Long recipePublishedById) {
        this.recipePublishedById = recipePublishedById;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
