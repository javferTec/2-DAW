package com.fpmislata.basespring.roles.admin.persistence.impl.jdbc.mapper;

import com.fpmislata.basespring.roles.admin.domain.model.AuthorAdmin;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorAdminRowMapper implements RowMapper<AuthorAdmin> {

    @Override
    public AuthorAdmin mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        AuthorAdmin authorAdmin = new AuthorAdmin();
        authorAdmin.setId(resultSet.getInt("authors.id"));
        authorAdmin.setName(resultSet.getString("authors.name"));
        authorAdmin.setNationality(resultSet.getString("authors.nationality"));
        authorAdmin.setBiographyEs(resultSet.getString("authors.biography_es"));
        authorAdmin.setBiographyEn(resultSet.getString("authors.biography_en"));
        authorAdmin.setBirthYear(resultSet.getInt("authors.birth_year"));
        authorAdmin.setDeathYear(resultSet.getInt("authors.death_year"));
        return authorAdmin;
    }
}