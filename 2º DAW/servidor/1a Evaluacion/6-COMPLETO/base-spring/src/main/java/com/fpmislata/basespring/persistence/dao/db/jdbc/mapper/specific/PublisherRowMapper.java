package com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.specific;

import com.fpmislata.basespring.domain.model.Publisher;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PublisherRowMapper implements RowMapper<Publisher> {

    @Override
    public Publisher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setId(resultSet.getLong("publishers.id"));
        publisher.setName(resultSet.getString("publishers.name"));
        publisher.setSlug(resultSet.getString("publishers.slug"));
        return publisher;
    }
}