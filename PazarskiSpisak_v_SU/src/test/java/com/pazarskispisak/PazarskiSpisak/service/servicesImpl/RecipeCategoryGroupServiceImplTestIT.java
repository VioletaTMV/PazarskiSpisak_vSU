package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.repository.RecipeCategoryGroupRepository;
import com.pazarskispisak.PazarskiSpisak.service.RecipeCategoryGroupService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeCategoryGroupServiceImplTestIT {

    @Autowired
    private RecipeCategoryGroupService recipeCategoryGroupServiceToTest;

    @Autowired
    private RecipeCategoryGroupRepository recipeCategoryGroupRepository;

    @BeforeEach
    void setUp(){
        recipeCategoryGroupRepository.deleteAll();
    }

    @AfterEach
    void tearDown(){
        recipeCategoryGroupRepository.deleteAll();
    }


    @Test
    void test_findAllOrderByGroupDisplayOrder() {


    }
}