package com.pazarskispisak.PazarskiSpisak.web;

import com.pazarskispisak.PazarskiSpisak.models.dtos.IngredientIdNameAndMeasurementValuesOnlyDTO;
import com.pazarskispisak.PazarskiSpisak.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ProductsControllerRest {

    private IngredientService ingredientService;

    @Autowired
    public ProductsControllerRest(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<IngredientIdNameAndMeasurementValuesOnlyDTO> ingredientIdNameAndMeasurementValues (@PathVariable("id") Long id) {

        IngredientIdNameAndMeasurementValuesOnlyDTO ingredientIdNameAndMeasuresOpt = ingredientService.findIngredientIdNameAndMeasuresById(id);

        if (ingredientIdNameAndMeasuresOpt == null){
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok(ingredientIdNameAndMeasuresOpt);
        }


    }
}
