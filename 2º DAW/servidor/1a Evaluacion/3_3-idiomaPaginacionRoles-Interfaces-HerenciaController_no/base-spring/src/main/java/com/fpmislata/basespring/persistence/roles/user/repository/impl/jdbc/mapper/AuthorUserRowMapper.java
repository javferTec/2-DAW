package com.fpmislata.basespring.persistence.roles.user.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.common.locale.LanguageUtils;
import com.fpmislata.basespring.domain.roles.user.model.AuthorUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorUserRowMapper implements RowMapper<AuthorUser> {

    @Override
    public AuthorUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        String language = LanguageUtils.getCurrentLanguage();
        AuthorUser authorUser = new AuthorUser();
        authorUser.setId(resultSet.getInt("authors.id"));
        authorUser.setName(resultSet.getString("authors.name"));
        authorUser.setNationality(resultSet.getString("authors.nationality"));
        authorUser.setBiography(resultSet.getString("authors.biography_" + language));
        authorUser.setBirthYear(resultSet.getInt("authors.birth_year"));
        authorUser.setDeathYear(resultSet.getInt("authors.death_year"));
        return authorUser;
    }
}
