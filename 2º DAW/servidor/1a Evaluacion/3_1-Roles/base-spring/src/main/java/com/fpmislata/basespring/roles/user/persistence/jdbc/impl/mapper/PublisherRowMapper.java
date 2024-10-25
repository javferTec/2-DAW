package com.fpmislata.basespring.roles.user.persistence.jdbc.impl.mapper;

import com.fpmislata.basespring.roles.user.domain.model.Publisher;
import com.fpmislata.basespring.common.layers.persistence.mappers.CustomRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherRowMapper implements CustomRowMapper<Publisher> {


    @Override
    public Publisher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setName(resultSet.getString("publishers.name"));
        publisher.setSlug(resultSet.getString("publishers.slug"));
        return publisher;
    }
}