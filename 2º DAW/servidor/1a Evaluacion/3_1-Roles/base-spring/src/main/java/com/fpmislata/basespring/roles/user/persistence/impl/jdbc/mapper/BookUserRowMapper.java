package com.fpmislata.basespring.roles.user.persistence.impl.jdbc.mapper;

import com.fpmislata.basespring.roles.user.domain.model.BookUser;
import com.fpmislata.basespring.common.layer.persistence.mapper.CustomRowMapper;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class BookUserRowMapper implements CustomRowMapper<BookUser> {

    private final CategoryUserRowMapper categoryUserRowMapper = new CategoryUserRowMapper();
    private final PublisherUserRowMapper publisherUserRowMapper = new PublisherUserRowMapper();

    @Override
    public BookUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        BookUser bookUser = new BookUser();
        bookUser.setIsbn(resultSet.getString("books.isbn"));
        bookUser.setTitle(resultSet.getString("books.title_" + getLanguage()));
        bookUser.setSynopsis(resultSet.getString("books.synopsis_" + getLanguage()));
        bookUser.setPrice(new BigDecimal(resultSet.getString("books.price")));
        bookUser.setDiscount(resultSet.getFloat("books.discount"));
        bookUser.setCover(resultSet.getString("books.cover"));
        if(this.existsColumn(resultSet, "publishers.id")) {
            bookUser.setPublisherUser(publisherUserRowMapper.mapRow(resultSet, rowNum));
        }
        if(this.existsColumn(resultSet, "categories.id")) {
            bookUser.setCategoryUser(categoryUserRowMapper.mapRow(resultSet, rowNum));
        }
        return bookUser;
    }
}