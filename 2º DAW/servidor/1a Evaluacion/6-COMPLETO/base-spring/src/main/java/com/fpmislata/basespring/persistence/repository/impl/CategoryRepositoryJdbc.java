package com.fpmislata.basespring.persistence.repository.impl;


import com.fpmislata.basespring.domain.model.Category;
import com.fpmislata.basespring.domain.repository.CategoryRepository;
import com.fpmislata.basespring.persistence.dao.db.CategoryDaoDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryJdbc implements CategoryRepository {

    private final CategoryDaoDb categoryDao;

    @Override
    public Optional<Category> getById(Long id) {
        return categoryDao.getById(id);
    }
}

