package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.*;
import com.pazarskispisak.PazarskiSpisak.models.entities.Recipe;
import com.pazarskispisak.PazarskiSpisak.models.entities.RecipeIngredientWithDetails;
import com.pazarskispisak.PazarskiSpisak.models.entities.User;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.repository.RecipeRepository;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeIngredientsService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import com.pazarskispisak.PazarskiSpisak.service.UserService;
import com.pazarskispisak.PazarskiSpisak.service.exception.ObjectNotFoundException;
import com.pazarskispisak.PazarskiSpisak.util.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.pazarskispisak.PazarskiSpisak.constants.UploadDirectory.UPLOAD_DIRECTORY;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final IngredientService ingredientService;
    private final RecipeIngredientsService recipeIngredientsService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, ModelMapper modelMapper, UserService userService, IngredientService ingredientService, RecipeIngredientsService recipeIngredientsService) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.ingredientService = ingredientService;
        this.recipeIngredientsService = recipeIngredientsService;
    }


    @Override
    public boolean areImported() {
        return this.recipeRepository.count() > 0;
    }

    @Override
    public void saveAll(List<Recipe> recipesToSaveToDB) {
        this.recipeRepository.saveAll(recipesToSaveToDB);
    }

    @Override
    public Optional<Recipe> findByLegacyCookId(int legacyCookId) {
        return this.recipeRepository.findByLegacyCookId(legacyCookId);
    }

//    @Override
//    public void save(Recipe recipe) {
//        this.recipeRepository.save(recipe);
//    }

    @Override
    public RecipeViewDTO getByRecipeId(Long id) {

        Optional<Recipe> recipe = this.recipeRepository.findById(id);

        if (recipe.isEmpty() || recipe.get().isDeleted()) {
            throw new ObjectNotFoundException("Рецепта с id " + id + " не съществува.");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String cookTime = getStringTime(recipe.get().getCookTime());
        String prepTime = getStringTime(recipe.get().getPrepTime());

        RecipeViewDTO recipeViewDTO = this.modelMapper.map(recipe, RecipeViewDTO.class)
                .setDateLastModified(dateTimeFormatter.format(recipe.get().getDateLastModified()))
                .setPublishedBy(recipe.get().getPublishedBy().getDisplayNickname())
                .setPrepTime(prepTime)
                .setCookTime(cookTime);

        Set<RecipeIngredientWithDetailsDTO> recipeIngredientWithDetailsDTOSet = new LinkedHashSet<>();

        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        for (RecipeIngredientWithDetails recipeIngredientWithDetails : recipe.get().getRecipeIngredients()) {

            String ingredientName = recipeIngredientWithDetails.getIngredient().getName();
            String ingredientQty = decimalFormat.format(recipeIngredientWithDetails.getIngredientMeasurementTotalValue());
            String measureBG = recipeIngredientWithDetails.getIngredientMeasurementUnitUsed().getMeasureBG();

            RecipeIngredientWithDetailsDTO recipeIngredientWithDetailsDTO = new RecipeIngredientWithDetailsDTO(ingredientName, measureBG, ingredientQty);

            recipeIngredientWithDetailsDTOSet.add(recipeIngredientWithDetailsDTO);
        }

        recipeViewDTO.setRecipeIngredientWithDetailsDTOSet(recipeIngredientWithDetailsDTOSet);

        //добавяме пълния път до снимката
        recipeViewDTO.setPicture(getRecipeImgPath(recipe.get().getPicture()));

        this.recipeRepository.increaseViewTimesByOne(recipe.get().getViewCount() + 1, recipe.get().getId());

        return recipeViewDTO;

    }

    @Override
    public Long saveWithProducts(RecipeAddDTO recipeModel, String email) {

        User recipePublisher = this.userService.findByEmail(email).get();

        Recipe newRecipe = this.modelMapper.map(recipeModel, Recipe.class);

        newRecipe.setPublishedBy(recipePublisher);

        Duration prepTime = getDuration(recipeModel.getPrepTimeMM(), recipeModel.getPrepTimeHH(), recipeModel.getPrepTimeDD());
        Duration cookTime = getDuration(recipeModel.getCookTimeMM(), recipeModel.getCookTimeHH());
        newRecipe.setPrepTime(prepTime);
        newRecipe.setCookTime(cookTime);

        Long savedNewRecipeId = this.recipeRepository.save(newRecipe).getId();


        List<RecipeIngredientWithDetailsAddDTO> recipeIngredientWithDetailsAddDTOList = recipeModel.getRecipeIngredientWithDetailsAddDTOList();

        for (RecipeIngredientWithDetailsAddDTO recipeIngredient : recipeIngredientWithDetailsAddDTOList) {

            newRecipe.addRecipeIngredientWithDetails(newRecipe,
                    this.ingredientService.findById(recipeIngredient.getIngredientId()).get(),
                    IngredientMeasurementUnitEnum.valueOf(recipeIngredient.getIngredientMeasurementUnitEnum()),
                    Double.valueOf(recipeIngredient.getQty()));

        }

        Set<RecipeIngredientWithDetails> recipeIngredientsToSaveToDB = newRecipe.getRecipeIngredients();

        this.recipeIngredientsService.saveAll(recipeIngredientsToSaveToDB.stream().toList());

        return savedNewRecipeId;
    }


    @Override
    public Optional<Recipe> findByRecipeNameAndCurrentlyLoggedUser(String recipeName, String email) {

        //Има ли по-добър вариант за намиране на текущо логнатия юзър (долния ред)
//        String currentlyLoggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> byDisplayNicknameOpt = this.userService.findByDisplayNickname(email);

        if (byDisplayNicknameOpt.isEmpty()) {
            return Optional.empty();
        }

        return this.recipeRepository.findByNameAndPublishedBy(recipeName, byDisplayNicknameOpt.get());
    }

//    @Override
//    public Long saveAndView(RecipeAddDTO recipeModel, String email) {
//        boolean saved = save(recipeModel);
//
//        if (!saved) {
//            return false;
//        }
//
//        Long recipeID = findByRecipeNameAndCurrentlyLoggedUser(recipeModel.getRecipeName(), this.).get().getId();
//
//        getByRecipeId(recipeID);
//
//        return true;
//    }

    @Override
    public Set<RecipeViewDTO> findAllByCategoryOrderedByDateLastModified(RecipeCategoryEnum recipeCategoryEnum) {

        List<Recipe> recipesByCategory = this.recipeRepository.findByRecipeCategoriesContainingOrderByDateLastModifiedDesc(recipeCategoryEnum);

        Set<RecipeViewDTO> recipeViewDTOSet = new LinkedHashSet<>();

        for (Recipe recipe : recipesByCategory) {

            String cookTime = getStringTime(recipe.getCookTime());
            String prepTime = getStringTime(recipe.getPrepTime());

            RecipeViewDTO recipeViewDTO = this.modelMapper.map(recipe, RecipeViewDTO.class)
                    .setCookTime(cookTime)
                    .setPrepTime(prepTime)
                    .setPublishedBy(recipe.getPublishedBy().getDisplayNickname())
                    .setPicture(getRecipeImgPath(recipe.getPicture()));

            recipeViewDTOSet.add(recipeViewDTO);
        }

        return recipeViewDTOSet;

    }

    @Override
    public boolean existsByRecipeCategory(RecipeCategoryEnum recipeCategoryEnum) {
        return this.recipeRepository.existsByRecipeCategories(recipeCategoryEnum);
    }

    @Override
    @Transactional
    public void deleteRecipeById(Long id) {

        Optional<Recipe> byId = this.recipeRepository.findById(id);

        if (byId.isEmpty()){
            throw new ObjectNotFoundException("Recipe with Id " + id + " doesn't exist.");
        }
        //да направя проверка дали юзъра, който се опитва да трие е автор на рецептата???

        this.recipeRepository.deleteById(id);


    }

    @Override
    public Optional<Recipe> findById(Long recipeId) {

        return this.recipeRepository.findById(recipeId);
    }

    @Override
    public String uploadPictureToDirAndGetFileName(MultipartFile multipartFile,
                                                   UserBasicDTO userBasicDTO,
                                                   RecipePictureAddDTO recipePictureAddDTO) throws IOException {
        String recipeImageName = null;
        System.out.println();
//        //долното дали не е излишно , дали не се проверява във Валидацията? Или не, защото не е задължително поле
//        if (multipartFile.isEmpty()) {
//            return Optional.empty();
//        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        recipeImageName = userBasicDTO.getId()
                + "_"
                + recipePictureAddDTO.getRecipeName().replaceAll(" ", "_");
        FileUploadUtil.saveFile(UPLOAD_DIRECTORY, recipeImageName, multipartFile);
        return recipeImageName;
    }
    @Override
    public RecipePictureAddDTO getRecipePictureAddDTO(Long recipeId) {
        Optional<Recipe> byId = this.recipeRepository.findById(recipeId);
        RecipePictureAddDTO recipePictureAddDTO = this.modelMapper.map(byId, RecipePictureAddDTO.class);
        System.out.println();
        return recipePictureAddDTO;
    }
    @Override
    @Transactional
    public void savePictureNameForRecipeInDB(Long recipeId, Long logedUserId, String recipeImageName) {
        Optional<Recipe> byIdOpt = this.recipeRepository.findById(recipeId);
        byIdOpt.get().setPicture(recipeImageName);
        this.recipeRepository.save(byIdOpt.get());
    }

    @Override
    public boolean isCurrentUserAllowedToUploadPictureForCurrentRecipe(UserBasicDTO userBasicDTO, RecipePictureAddDTO recipePictureAddDTO) {
        Optional<Recipe> recipeOpt = this.recipeRepository.findById(recipePictureAddDTO.getRecipeId());
        boolean isCurrentUserThePublisherOfCurrentRecipe = userBasicDTO.getId().equals(recipePictureAddDTO.getRecipePublishedById());
        if (recipeOpt.isEmpty() ||
                !isCurrentUserThePublisherOfCurrentRecipe) {
            return false;
        }
        return true;
    }

    private Duration getDuration(Short... modelPrepTimesFromMinutesToDays) {

        Duration duration = Duration.ZERO;

        Short prepTimeMM = modelPrepTimesFromMinutesToDays[0];
        Short prepTimeHH = modelPrepTimesFromMinutesToDays[1];
        Short prepTimeDD = null;

        if (modelPrepTimesFromMinutesToDays.length == 3) {
            prepTimeDD = modelPrepTimesFromMinutesToDays[2];
        }

        if (prepTimeDD == null && prepTimeHH == null && prepTimeMM == null) {
            return null;
        }

        if (prepTimeMM != null) {
            duration = duration.plusMinutes(prepTimeMM);
        }

        if (prepTimeHH != null) {
            duration = duration.plusHours(prepTimeHH);
        }

        if (modelPrepTimesFromMinutesToDays.length == 3 && prepTimeDD != null) {
            duration = duration.plusDays(prepTimeDD);
        }

        return duration;

    }


    private String getStringTime(Duration duration) {

        StringBuilder cookTime = new StringBuilder();

        if (null != duration) {

            long daysPart = duration.toDaysPart();
            int hoursPart = duration.toHoursPart();
            int minutesPart = duration.toMinutesPart();

            if (daysPart > 0) {
                cookTime.append(String.valueOf(daysPart)).append("d");
            }
            if (hoursPart > 0) {
                cookTime.append(String.valueOf(hoursPart)).append("h");
            }
            if (minutesPart > 0) {
                cookTime.append(String.valueOf(minutesPart)).append("min");
            }
        } else {
            cookTime.append("N/A");
        }

        return cookTime.toString();
    }

    @Override
    public String getRecipeImgPath(String pictureName) {
        if (pictureName == null) {
            return null;
        }
        return "/" + UPLOAD_DIRECTORY + "/" + pictureName;
    }
}
