package com.fpmislata.basespring.roles.admin.persistence.impl.jdbc.mapper;

import com.fpmislata.basespring.roles.admin.domain.model.PublisherAdmin;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherAdminRowMapper implements RowMapper<PublisherAdmin> {

    @Override
    public PublisherAdmin mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        PublisherAdmin publisherAdmin = new PublisherAdmin();
        publisherAdmin.setId(resultSet.getInt("publishers.id"));
        publisherAdmin.setName(resultSet.getString("publishers.name"));
        publisherAdmin.setSlug(resultSet.getString("publishers.slug"));
        return publisherAdmin;
    }
}