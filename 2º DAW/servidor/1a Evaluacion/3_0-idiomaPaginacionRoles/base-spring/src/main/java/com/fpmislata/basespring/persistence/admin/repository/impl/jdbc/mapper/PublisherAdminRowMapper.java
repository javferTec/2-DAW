package com.fpmislata.basespring.persistence.admin.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.domain.admin.model.PublisherAdmin;
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