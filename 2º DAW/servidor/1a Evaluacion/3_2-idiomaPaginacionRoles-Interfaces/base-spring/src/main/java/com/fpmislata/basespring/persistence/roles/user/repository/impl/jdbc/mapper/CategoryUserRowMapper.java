package com.fpmislata.basespring.persistence.roles.user.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.common.locale.LanguageUtils;
import com.fpmislata.basespring.domain.roles.user.model.CategoryUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryUserRowMapper implements RowMapper<CategoryUser> {

    @Override
    public CategoryUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        String language = LanguageUtils.getCurrentLanguage();
        CategoryUser categoryUser = new CategoryUser();
        categoryUser.setId(rs.getInt("categories.id"));
        categoryUser.setName(rs.getString("categories.name_" + language));
        categoryUser.setSlug(rs.getString("categories.slug"));
        return categoryUser;
    }
}
