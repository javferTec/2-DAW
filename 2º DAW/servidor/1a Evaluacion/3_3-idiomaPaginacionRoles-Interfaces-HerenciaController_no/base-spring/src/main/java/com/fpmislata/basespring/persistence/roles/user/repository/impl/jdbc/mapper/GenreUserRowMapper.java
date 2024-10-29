package com.fpmislata.basespring.persistence.roles.user.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.common.locale.LanguageUtils;
import com.fpmislata.basespring.domain.roles.user.model.GenreUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreUserRowMapper implements RowMapper<GenreUser> {

    @Override
    public GenreUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        String language = LanguageUtils.getCurrentLanguage();
        GenreUser genreUser = new GenreUser();
        genreUser.setId(rs.getInt("genres.id"));
        genreUser.setName(rs.getString("genres.name_" + language));
        genreUser.setSlug(rs.getString("genres.slug"));
        return genreUser;
    }
}
