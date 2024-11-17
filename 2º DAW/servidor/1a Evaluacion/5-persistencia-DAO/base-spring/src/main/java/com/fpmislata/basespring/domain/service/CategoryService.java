package com.fpmislata.basespring.domain.service;

import com.fpmislata.basespring.domain.model.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(Long id);
}
