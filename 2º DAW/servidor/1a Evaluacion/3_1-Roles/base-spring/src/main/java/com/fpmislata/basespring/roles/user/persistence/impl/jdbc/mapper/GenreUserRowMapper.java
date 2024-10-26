package com.fpmislata.basespring.roles.user.persistence.impl.jdbc.mapper;

import com.fpmislata.basespring.common.locale.LanguageUtils;
import com.fpmislata.basespring.roles.user.domain.model.GenreUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreUserRowMapper implements RowMapper<GenreUser> {

    @Override
    public GenreUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        String language = LanguageUtils.getCurrentLanguage();
        GenreUser genreUser = new GenreUser();
        genreUser.setName(resultSet.getString("genres.name_" + language));
        genreUser.setSlug(resultSet.getString("genres.slug"));
        return genreUser;
    }
}
