package com.fpmislata.basespring.persistence.repository.impl.jdbc;


import com.fpmislata.basespring.domain.model.Category;
import com.fpmislata.basespring.domain.repository.CategoryRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.DynamicSQLBuilder;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.CategoryRowMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Getter
@Repository
@RequiredArgsConstructor
public class CategoryRepositoryJdbc implements CategoryRepository {

    private final DynamicSQLBuilder<Category> sqlBuilder;

    private static final String TABLE_NAME = "categories";
    private static final List<String> ID_COLUMNS = List.of("id"); // Suponemos que "id" es el identificador

    @Autowired
    public CategoryRepositoryJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.sqlBuilder = new DynamicSQLBuilder<>(
                jdbcTemplate,
                namedParameterJdbcTemplate,
                TABLE_NAME,
                ID_COLUMNS,
                new CategoryRowMapper()
        );
    }

    @Override
    public Optional<Category> findById(Long id) {
        return sqlBuilder.findById(ID_COLUMNS.getFirst(), id);
    }
}

