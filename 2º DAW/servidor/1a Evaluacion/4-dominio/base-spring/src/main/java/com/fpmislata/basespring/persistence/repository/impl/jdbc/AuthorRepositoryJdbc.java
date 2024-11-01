package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.repository.AuthorRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.DynamicSQLBuilder;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.AuthorRowMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Getter
@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final DynamicSQLBuilder<Author> dynamicSQLBuilder;

    private static final String TABLE_NAME = "authors";
    private static final String ISBN_COLUMN = "isbn";
    private static final String ID_COLUMN = "id";
    private static final String SELECT_AUTHORS = "SELECT authors.*";
    private static final String JOIN_CLAUSE_ISBN = "JOIN books_authors ON authors.id = books_authors.author_id JOIN books ON books_authors.book_id = books.id";
    private static final String JOIN_CLAUSE_ID_BOOK = "JOIN books_authors ON authors.id = books_authors.author_id";

    @Autowired
    public AuthorRepositoryJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dynamicSQLBuilder = new DynamicSQLBuilder<>(
                jdbcTemplate,
                namedParameterJdbcTemplate,
                TABLE_NAME,
                List.of(ID_COLUMN),
                new AuthorRowMapper()
        );
    }

    @Override
    public List<Author> getByIsbnBook(String isbn) {
        return dynamicSQLBuilder.findByCriteria(SELECT_AUTHORS, JOIN_CLAUSE_ISBN, Collections.singletonMap(ISBN_COLUMN, isbn));

    }

    @Override
    public List<Author> getByIdBook(long id) {
        return dynamicSQLBuilder.findByCriteria(SELECT_AUTHORS, JOIN_CLAUSE_ID_BOOK, Collections.singletonMap(ID_COLUMN, id));
    }

    @Override
    public List<Author> findAllById(Long[] ids) {
        return dynamicSQLBuilder.findByIds(List.of(ids));
    }
}