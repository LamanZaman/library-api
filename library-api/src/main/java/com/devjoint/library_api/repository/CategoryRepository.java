package com.devjoint.library_api.repository;

import com.devjoint.library_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}