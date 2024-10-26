package com.fpmislata.basespring.roles.user.persistence.impl.jdbc.mapper;

import com.fpmislata.basespring.common.locale.LanguageUtils;
import com.fpmislata.basespring.roles.user.domain.model.CategoryUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryUserRowMapper implements RowMapper<CategoryUser> {

    @Override
    public CategoryUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        String language = LanguageUtils.getCurrentLanguage();
        CategoryUser categoryUser = new CategoryUser();
        categoryUser.setName(resultSet.getString("categories.name_" + language));
        categoryUser.setSlug(resultSet.getString("categories.slug"));
        return categoryUser;
    }
}
