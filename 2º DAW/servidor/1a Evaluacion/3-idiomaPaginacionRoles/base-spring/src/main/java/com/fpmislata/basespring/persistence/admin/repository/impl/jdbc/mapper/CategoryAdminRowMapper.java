package com.fpmislata.basespring.persistence.admin.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.domain.admin.model.CategoryAdmin;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryAdminRowMapper implements RowMapper<CategoryAdmin> {

    @Override
    public CategoryAdmin mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoryAdmin categoryAdmin = new CategoryAdmin();
        categoryAdmin.setId(rs.getLong("categories.id"));
        categoryAdmin.setNameEs(rs.getString("categories.name_es"));
        categoryAdmin.setNameEn(rs.getString("categories.name_en"));
        categoryAdmin.setSlug(rs.getString("categories.slug"));
        return categoryAdmin;
    }
}