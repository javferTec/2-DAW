package com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.domain.model.Publisher;
import com.fpmislata.basespring.persistence.common.CustomRowMapper;
import org.springframework.jdbc.core.RowMapper;

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