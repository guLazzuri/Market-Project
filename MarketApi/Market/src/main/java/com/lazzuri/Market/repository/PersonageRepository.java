package com.lazzuri.Market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lazzuri.Market.model.ClassPersonageType;
import com.lazzuri.Market.model.Personage;

public interface  PersonageRepository extends JpaRepository<Personage, Long>, JpaSpecificationExecutor<Personage> {

    List<Personage> findByNameContainingIgnoreCase(String name);
    List<Personage> findByClassType(ClassPersonageType classType);

}
