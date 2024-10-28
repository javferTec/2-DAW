package com.fpmislata.basespring.persistence.user.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.domain.user.model.PublisherUser;
import com.fpmislata.basespring.persistence.common.CustomRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherUserRowMapper implements CustomRowMapper<PublisherUser> {


    @Override
    public PublisherUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        PublisherUser publisherUser = new PublisherUser();
        publisherUser.setName(resultSet.getString("publishers.name"));
        publisherUser.setSlug(resultSet.getString("publishers.slug"));
        return publisherUser;
    }
}