package com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.domain.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    private final CategoryRowMapper categoryRowMapper = new CategoryRowMapper();
    private final PublisherRowMapper publisherRowMapper = new PublisherRowMapper();

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setIsbn(rs.getString("isbn"));
        book.setTitle(rs.getString("title_es"));
        book.setTitle(rs.getString("title_en"));
        book.setSynopsis(rs.getString("synopsis_es"));
        book.setSynopsis(rs.getString("synopsis_en"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setDiscount(rs.getFloat("discount"));
        book.setCover(rs.getString("cover"));
        if (rs.getInt("category_id") != 0) {
            book.setCategory(categoryRowMapper.mapRow(rs, rowNum));
        }
        if (rs.getInt("publisher_id") != 0) {
            book.setPublisher(publisherRowMapper.mapRow(rs, rowNum));
        }
        return book;
    }
}
