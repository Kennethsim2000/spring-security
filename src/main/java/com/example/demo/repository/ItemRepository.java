package com.example.demo.repository;

import com.example.demo.models.Item;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Boolean existsByItemName(String name);
}
