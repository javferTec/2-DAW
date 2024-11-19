package com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.specific;

import com.fpmislata.basespring.domain.model.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getLong("genres.id"));
        genre.setNameEs(rs.getString("genres.name_es"));
        genre.setNameEn(rs.getString("genres.name_en"));
        genre.setSlug(rs.getString("genres.slug"));
        return genre;
    }
}