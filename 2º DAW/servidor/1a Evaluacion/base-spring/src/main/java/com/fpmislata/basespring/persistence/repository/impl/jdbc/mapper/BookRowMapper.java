package com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper;

import com.fpmislata.basespring.domain.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setIsbn(rs.getString("isbn"));
        book.setTitle(rs.getString("title"));
        book.setSynopsis(rs.getString("synopsis"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setDiscount(rs.getFloat("discount"));
        book.setCover(rs.getString("cover"));
        return book;
    }
}
