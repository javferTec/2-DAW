package com.fpmislata.basespring.roles.admin.persistence.impl.jdbc.mapper;

import com.fpmislata.basespring.common.layer.persistence.mapper.CustomRowMapper;
import com.fpmislata.basespring.roles.admin.domain.model.BookAdmin;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookAdminRowMapper implements CustomRowMapper<BookAdmin> {

    private final PublisherAdminRowMapper publisherAdminRowMapper = new PublisherAdminRowMapper();
    private final CategoryAdminRowMapper categoryAdminRowMapper = new CategoryAdminRowMapper();

    @Override
    public BookAdmin mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        BookAdmin bookAdmin = new BookAdmin();
        bookAdmin.setId(resultSet.getLong("books.id"));
        bookAdmin.setIsbn(resultSet.getString("books.isbn"));
        bookAdmin.setTitleEs(resultSet.getString("books.title_es"));
        bookAdmin.setTitleEn(resultSet.getString("books.title_en"));
        bookAdmin.setSynopsisEs(resultSet.getString("books.synopsis_es"));
        bookAdmin.setSynopsisEn(resultSet.getString("books.synopsis_en"));
        bookAdmin.setPrice(new BigDecimal(resultSet.getString("books.price")));
        bookAdmin.setDiscount(resultSet.getFloat("books.discount"));
        bookAdmin.setCover(resultSet.getString("books.cover"));
        if(this.existsColumn(resultSet, "publishers.id")) {
            bookAdmin.setPublisherAdmin(publisherAdminRowMapper.mapRow(resultSet, rowNum));
        }
        if(this.existsColumn(resultSet, "categories.id")) {
            bookAdmin.setCategoryAdmin(categoryAdminRowMapper.mapRow(resultSet, rowNum));
        }
        return bookAdmin;
    }
}