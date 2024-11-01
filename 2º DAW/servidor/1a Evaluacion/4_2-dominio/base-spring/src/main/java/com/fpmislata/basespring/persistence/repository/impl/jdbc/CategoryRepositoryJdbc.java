package com.fpmislata.basespring.persistence.repository.impl.jdbc;


import com.fpmislata.basespring.domain.model.Category;
import com.fpmislata.basespring.domain.repository.CategoryRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.JdbcOperations;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.SqlBuilder;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.CategoryRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryJdbc implements CategoryRepository {

    private final JdbcOperations jdbcOperations;
    private final SqlBuilder sqlBuilder;
    private final CategoryRowMapper categoryRowMapper;

    // Constantes para nombres de tabla y columnas
    private static final String TABLE_NAME = "categories";
    private static final String ID_COLUMN = "id";

    @Override
    public Optional<Category> findById(Long id) {
        String sql = sqlBuilder.findByColumn(TABLE_NAME, ID_COLUMN);
        return jdbcOperations.findById(sql, id, categoryRowMapper);
    }
}

