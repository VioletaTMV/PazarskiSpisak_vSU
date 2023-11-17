package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientIdNameAndMeasurementValuesOnlyDTO;
import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeIngredientWithDetailsAddDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.Ingredient;
import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
import com.pazarskispisak.PazarskiSpisak.repository.IngredientRepository;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import com.pazarskispisak.PazarskiSpisak.service.ItemCategoryService;
import com.pazarskispisak.PazarskiSpisak.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private ModelMapper modelMapper;
    private ItemCategoryService itemCategoryService;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, ModelMapper modelMapper, ItemService itemService, ItemCategoryService itemCategoryService) {
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
        this.itemCategoryService = itemCategoryService;
    }

    @Override
    public boolean areImported() {
        return this.ingredientRepository.count() > 0;
    }

    @Override
    public void save(Ingredient ingredient) {
        this.ingredientRepository.save(ingredient);
    }

    @Override
    public void saveAll(List<Ingredient> ingredientsToSaveToDB) {
        this.ingredientRepository.saveAll(ingredientsToSaveToDB);
    }

    @Override
    public Optional<Ingredient> findByLegacyArticleId(int legacyArticleId) {
        return this.ingredientRepository.findByLegacyArticleId(legacyArticleId);
    }

    @Override
    public IngredientMeasurementUnitEnum findMainUnitOfMeasurement(Ingredient ingredient) {
        return this.ingredientRepository.findIngredientMainUnitOfMeasurementById(ingredient.getId());
    }

    @Override
    public List<IngredientDTO> getAllIngredientsSortedAlphabetically() {

        //да го коригирам така че да не връща цялото ентити а само имената на продуктите
        List<Ingredient> allIngredients = this.ingredientRepository.findAll();

        List<IngredientDTO> ingredientDTOS = allIngredients.stream()
                .map(i -> this.modelMapper.map(i, IngredientDTO.class))
                .sorted(Comparator.comparing(IngredientDTO::getName))
                .toList();

        return ingredientDTOS;
    }

    @Override
    public List<IngredientMeasurementUnitEnum> getOnlyMainUnitsOfMeasureForIngredients() {

        IngredientMeasurementUnitEnum gram = IngredientMeasurementUnitEnum.GRAM;
        IngredientMeasurementUnitEnum milliliter = IngredientMeasurementUnitEnum.MILLILITER;

        return Arrays.asList(gram, milliliter);
    }

    @Override
    public List<IngredientMeasurementUnitEnum> getOnlyQuantifiableAlternativeUnitsOfMeasureForIngredients() {

        return Arrays.stream(IngredientMeasurementUnitEnum.values())
                .filter(u -> !getOnlyMainUnitsOfMeasureForIngredients().contains(u) &&
                        !IngredientMeasurementUnitEnum.EMPTY.equals(u) &&
                        !IngredientMeasurementUnitEnum.AT_TASTE.equals(u))
                .toList();
    }

    @Override
    public Map<IngredientMeasurementUnitEnum, Float> populateIngredientDTOAltUnitsOfMeasureAndValueWithData() {

        Map<IngredientMeasurementUnitEnum, Float> prepolupatedMap = new LinkedHashMap<>();

        for (IngredientMeasurementUnitEnum u : getOnlyQuantifiableAlternativeUnitsOfMeasureForIngredients()) {

            prepolupatedMap.put(u, null);

        }

        return prepolupatedMap;

    }

    @Override
    public boolean saveNewIngredient(IngredientDTO ingredientDTO) {

        Ingredient newIngredient = this.modelMapper.map(ingredientDTO, Ingredient.class);

        newIngredient.setItemCategory(this.itemCategoryService.findByName(newIngredient.getItemCategory().getName()).get());

        Map<IngredientMeasurementUnitEnum, Float> onlyNonNullAltMUValues = newIngredient.getIngredientAltMUVMap().entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        newIngredient.setIngredientAltMUVMap(onlyNonNullAltMUValues);

        try {
            this.ingredientRepository.save(newIngredient);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public IngredientIdNameAndMeasurementValuesOnlyDTO findIngredientIdNameAndMeasuresById(Long id) {

        Optional<Ingredient> ingredientOpt = this.ingredientRepository.findById(id);

        if (ingredientOpt.isEmpty()) {
            return null;
        }

        return new IngredientIdNameAndMeasurementValuesOnlyDTO(ingredientOpt.get());
    }

    @Override
    public Map<IngredientMeasurementUnitEnum, String> getAllowedMeasurementUnitEnumsForProduct(Long ingredientId) {

       return findIngredientIdNameAndMeasuresById(ingredientId).getAcceptableMeasurementUnitsForRecipeDescriptionMap();

    }

    @Override
    public void addAllowedMeasurementUnitsForEachProductSelectedToBindToItsOptionsOnReload(List<RecipeIngredientWithDetailsAddDTO> recipeIngredientWithDetailsAddDTOList) {

        recipeIngredientWithDetailsAddDTOList.forEach(ingrWithDetails -> {

            if (ingredientExists(ingrWithDetails.getIngredientId())){

                Map<IngredientMeasurementUnitEnum, String> allowedMeasurementUnitsForProduct = getAllowedMeasurementUnitEnumsForProduct(ingrWithDetails.getIngredientId());
                ingrWithDetails.setAllowedIngredientMeasurementUnitEnumForProductMap(allowedMeasurementUnitsForProduct);

            }
        });

    }

    public boolean ingredientExists(Long ingredientId) {

        if (ingredientId == null){
            return false;
        } else if (this.ingredientRepository.findById(ingredientId).isEmpty()){
            return false;
        }

        return true;
    }

    @Override
    public Optional <Ingredient> findById(Long value) {

        return this.ingredientRepository.findById(value);

    }


}
