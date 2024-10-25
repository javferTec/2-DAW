package com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.persistence.common.CustomRowMapper;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class BookRowMapper implements CustomRowMapper<Book> {

    private final CategoryRowMapper categoryRowMapper = new CategoryRowMapper();
    private final PublisherRowMapper publisherRowMapper = new PublisherRowMapper();

    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();
        book.setIsbn(resultSet.getString("books.isbn"));
        book.setTitle(resultSet.getString("books.title_" + getLanguage()));
        book.setSynopsis(resultSet.getString("books.synopsis_" + getLanguage()));
        book.setPrice(new BigDecimal(resultSet.getString("books.price")));
        book.setDiscount(resultSet.getFloat("books.discount"));
        book.setCover(resultSet.getString("books.cover"));
        if(this.existsColumn(resultSet, "publishers.id")) {
            book.setPublisher(publisherRowMapper.mapRow(resultSet, rowNum));
        }
        if(this.existsColumn(resultSet, "categories.id")) {
            book.setCategory(categoryRowMapper.mapRow(resultSet, rowNum));
        }
        return book;
    }
}