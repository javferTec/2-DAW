package com.fpmislata.basespring.domain.service.impl;

import com.fpmislata.basespring.common.annotation.DomainService;
import com.fpmislata.basespring.domain.model.Category;
import com.fpmislata.basespring.domain.repository.CategoryRepository;
import com.fpmislata.basespring.domain.service.CategoryService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
}