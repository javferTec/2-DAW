package com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.domain.model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("categories.id"));
        category.setName(rs.getString("categories.name_es"));
        category.setName(rs.getString("categories.name_en"));
        category.setSlug(rs.getString("categories.slug"));
        return category;
    }
}
