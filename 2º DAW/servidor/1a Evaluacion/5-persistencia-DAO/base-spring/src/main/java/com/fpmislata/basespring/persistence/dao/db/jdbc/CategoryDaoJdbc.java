package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Category;
import com.fpmislata.basespring.persistence.dao.db.CategoryDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.CategoryRowMapper;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.GenericRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

@Dao
@RequiredArgsConstructor
public class CategoryDaoJdbc implements CategoryDaoDb {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Category> findById(long id) {
        String sql = """
                        SELECT * FROM categories
                        WHERE id = ?
                     """;
        try {
            // Usamos el GenericRowMapper para mapear la fila a la entidad Category
            Category category = jdbcTemplate.queryForObject(sql, new GenericRowMapper<>(Category.class, jdbcTemplate), id);
            return Optional.ofNullable(category);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Category> getAll() {
        //TODO: Implementar obtener todas las categorias
        return List.of();
    }

    @Override
    public List<Category> getAll(int page, int size) {
        //TODO: Implementar obtener todas las categorias paginadas
        return List.of();
    }

    @Override
    public long insert(Category category) {
        //TODO: Implementar insertar una categoria
        return 0;
    }

    @Override
    public void update(Category category) {
        //TODO: Implementar actualizar una categoria
    }

    @Override
    public void delete(long id) {
        //TODO: Implementar borrar una categoria
    }

    @Override
    public int count() {
        return 0;
    }
}
