package com.pazarskispisak.PazarskiSpisak.service;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipePictureAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeViewDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.UserBasicDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.Recipe;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    boolean areImported();

    void saveAll(List<Recipe> recipesToSaveToDB);

    Optional<Recipe> findByLegacyCookId(int legacyCookId);

//    void save(Recipe recipe);

    //надолу са вече сървиси за самото приложение извън импорта на старите данни

    RecipeViewDTO getByRecipeId (Long id);

    Long saveWithProducts(RecipeAddDTO recipeModel, String email);

    Optional<Recipe> findByRecipeNameAndCurrentlyLoggedUser(String recipeName, String email);

//    Long saveAndView(RecipeAddDTO recipeModel, String email);

    Set<RecipeViewDTO> findAllByCategoryOrderedByDateLastModified(RecipeCategoryEnum recipeCategoryEnum);

    boolean existsByRecipeCategory(RecipeCategoryEnum recipeCategoryEnum);

    void deleteRecipeById(Long id);

    Optional<Recipe> findById(Long recipeId);


    String uploadPictureToDirAndGetFileName(MultipartFile multipartFile,
                                            UserBasicDTO userBasicDTO,
                                            RecipePictureAddDTO recipePictureAddDTO) throws IOException;
    RecipePictureAddDTO getRecipePictureAddDTO(Long recipeId);
    void savePictureNameForRecipeInDB(Long recipeId, Long logedUserId, String recipeImageName);
    boolean isCurrentUserAllowedToUploadPictureForCurrentRecipe(UserBasicDTO userBasicDTO, RecipePictureAddDTO recipePictureAddDTO);
    String getRecipeImgPath(String pictureName);
}
