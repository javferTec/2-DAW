package com.fpmislata.basespring.persistence.admin.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.domain.admin.model.GenreAdmin;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreAdminRowMapper implements RowMapper<GenreAdmin> {
    @Override
    public GenreAdmin mapRow(ResultSet rs, int rowNum) throws SQLException {
        GenreAdmin genreAdmin = new GenreAdmin();
        genreAdmin.setId(rs.getLong("genres.id"));
        genreAdmin.setNameEs(rs.getString("genres.name_es"));
        genreAdmin.setNameEn(rs.getString("genres.name_en"));
        genreAdmin.setSlug(rs.getString("genres.slug"));
        return genreAdmin;
    }
}