package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.dtos.RecipeCategoryGroupDTO;
import com.pazarskispisak.PazarskiSpisak.models.entities.RecipeCategoryGroup;
import com.pazarskispisak.PazarskiSpisak.models.enums.RecipeCategoryEnum;
import com.pazarskispisak.PazarskiSpisak.repository.RecipeCategoryGroupRepository;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import com.pazarskispisak.PazarskiSpisak.service.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeCategoryGroupServiceImplTest {

    private RecipeCategoryGroupService serviceToTest;

    @Mock
    private RecipeCategoryGroupRepository mockedRecipeCategoryGroupRepository;
    @Mock
    private ModelMapper mockedModelMapper;
    @Mock
    private RecipeService mockedRecipeService;

    @BeforeEach
    void setUp(){
        serviceToTest = new RecipeCategoryGroupServiceImpl(
                mockedRecipeCategoryGroupRepository,
                mockedModelMapper,
                mockedRecipeService);
    }

    @Test
    void test_getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes(){

        RecipeCategoryGroup testRCG1 = createTestRCG("По осн. съставка", 1,
                Set.of(RecipeCategoryEnum.VEAL_BEEF, RecipeCategoryEnum.DUCK_GOOSE));
        RecipeCategoryGroup testRCG2 = createTestRCG("По степен", 2,
                Set.of(RecipeCategoryEnum.SOUP, RecipeCategoryEnum.MAIN_COURSE));
        List<RecipeCategoryGroup> testRCGList = List.of(testRCG1, testRCG2);
        doReturn(testRCGList).when(mockedRecipeCategoryGroupRepository).findAll();

        RecipeCategoryGroupDTO testRCGDTO1 = createTestRCGDTO(testRCG1);
        RecipeCategoryGroupDTO testRCGDTO2 = createTestRCGDTO(testRCG2);
        RecipeCategoryGroupDTO[] testRCGDTOs = new RecipeCategoryGroupDTO[] {testRCGDTO1, testRCGDTO2};
        doReturn(testRCGDTOs).when(mockedModelMapper).map(testRCGList, RecipeCategoryGroupDTO[].class);

        when(mockedRecipeService.existsByRecipeCategory(RecipeCategoryEnum.SOUP))
                .thenReturn(true);
        when(mockedRecipeService.existsByRecipeCategory(RecipeCategoryEnum.MAIN_COURSE))
                .thenReturn(true);
        when(mockedRecipeService.existsByRecipeCategory(RecipeCategoryEnum.VEAL_BEEF))
                .thenReturn(true);
        when(mockedRecipeService.existsByRecipeCategory(RecipeCategoryEnum.DUCK_GOOSE))
                .thenReturn(true);

        List<RecipeCategoryGroupDTO> testResult = serviceToTest.getAllCategoryGroupsHavingCategoryRecipesWithActiveAssignedRecipes();

        assertEquals(2, testResult.size());
        assertTrue(testResult.contains(testRCGDTO1));
        assertTrue(testResult.contains(testRCGDTO2));
    }

    private RecipeCategoryGroupDTO createTestRCGDTO(RecipeCategoryGroup testRCG1) {

        RecipeCategoryGroupDTO testRCGDTO = new RecipeCategoryGroupDTO();
        testRCGDTO.setGroupName(testRCG1.getGroupName());
        testRCGDTO.setGroupDisplayOrder(testRCG1.getGroupDisplayOrder());
        testRCGDTO.setRecipeCategories(testRCG1.getRecipeCategories());
        return testRCGDTO;
    }

    private RecipeCategoryGroup createTestRCG(String name, int displayOrder, Set<RecipeCategoryEnum> rc) {

        RecipeCategoryGroup testRCG = new RecipeCategoryGroup();
        testRCG.setGroupName(name);
        testRCG.setGroupDisplayOrder(displayOrder);
        testRCG.setRecipeCategories(rc);

        return testRCG;
    }

}