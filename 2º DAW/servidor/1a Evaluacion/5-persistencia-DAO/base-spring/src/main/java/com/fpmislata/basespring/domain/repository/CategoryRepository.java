package com.fpmislata.basespring.domain.repository;

import com.fpmislata.basespring.domain.model.Category;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(Long id);
}