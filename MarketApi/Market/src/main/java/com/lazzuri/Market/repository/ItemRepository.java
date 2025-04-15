package com.lazzuri.Market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lazzuri.Market.model.Item;
import com.lazzuri.Market.model.RareItem;
import com.lazzuri.Market.model.TypeItem;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    List<Item> findByNameContainingIgnoreCase(String name);
    List<Item> findByTypeItem(TypeItem typeItem);
    List<Item> findByRareItem(RareItem rareItem);
    List<Item> findByPriceBetween(Integer minPrice, Integer maxPrice);
}
