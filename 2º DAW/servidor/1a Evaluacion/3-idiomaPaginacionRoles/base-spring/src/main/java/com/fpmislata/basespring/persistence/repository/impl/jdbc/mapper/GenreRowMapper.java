package com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.common.locale.LanguageUtils;
import com.fpmislata.basespring.domain.model.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        String language = LanguageUtils.getCurrentLanguage();
        Genre genre = new Genre();
        genre.setId(rs.getInt("genres.id"));
        genre.setName(rs.getString("genres.name_" + language));
        genre.setSlug(rs.getString("genres.slug"));
        return genre;
    }
}
