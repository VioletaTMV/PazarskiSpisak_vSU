package com.pazarskispisak.PazarskiSpisak.repository;

import com.pazarskispisak.PazarskiSpisak.models.entities.RecipeCategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeCategoryGroupRepository extends JpaRepository<RecipeCategoryGroup, Long> {


    List<RecipeCategoryGroup> findByOrderByGroupDisplayOrderAsc();
}
