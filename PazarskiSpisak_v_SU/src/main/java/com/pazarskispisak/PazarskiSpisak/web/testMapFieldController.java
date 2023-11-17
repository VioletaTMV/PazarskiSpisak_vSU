//package com.pazarskispisak.PazarskiSpisak.web;
//
//import com.pazarskispisak.PazarskiSpisak.models.dtos.TestMapFieldDTO;
//import com.pazarskispisak.PazarskiSpisak.models.enums.IngredientMeasurementUnitEnum;
//import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class testMapFieldController {
//
//    private IngredientService ingredientService;
//@Autowired
//    public testMapFieldController(IngredientService ingredientService) {
//        this.ingredientService = ingredientService;
//    }
//
//    @ModelAttribute("testModel")
//    public TestMapFieldDTO initTestObjectWithMap(){
//
//        TestMapFieldDTO testMapFieldDTO = new TestMapFieldDTO();
//        Map<IngredientMeasurementUnitEnum, Float> prepopulatedMap = this.ingredientService.populateIngredientDTOAltUnitsOfMeasureAndValueWithData();
//        testMapFieldDTO.setMapIngrMU(prepopulatedMap);
//
//        return testMapFieldDTO;
//    }
//
//    @ModelAttribute("alternativeIngredientUnitsOfMeasure")
//    public List<IngredientMeasurementUnitEnum> alternativeMeasuremtnUnitsOnly() {
//        return this.ingredientService.getOnlyQuantifiableAlternativeUnitsOfMeasureForIngredients();
//    }
//
//
//
//    @GetMapping("/test")
//    public String test(){
//
//        System.out.println();
//
//        return "/test-MapFieldInDTO";
//    }
//
//    @PostMapping("/test")
//    public String testPost(@ModelAttribute("testModel") TestMapFieldDTO testMapFieldDTO,
//                           BindingResult bindingResult,
//                           RedirectAttributes redirectAttributes) {
//        System.out.println();
//    return "redirect:/";
//    }
//
//    @PostMapping(value = "/test", params = {"addTestBtn"})
//    public String add(@ModelAttribute("testModel") TestMapFieldDTO testMapFieldDTO,
//                      BindingResult bindingResult){
//
//        System.out.println();
//
//    return "/test";
//    }
//}
