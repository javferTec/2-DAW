package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.domain.repository.GenreRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.DynamicSQLBuilder;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.GenreRowMapper;
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
public class GenreRepositoryJdbc implements GenreRepository {

    private final DynamicSQLBuilder<Genre> dynamicSQLBuilder;

    private static final String TABLE_NAME = "genres";
    private static final String ID_COLUMN = "id";
    private static final String ISBN_COLUMN = "isbn";
    private static final String SELECT_GENRES = "SELECT genres.*";
    private static final String JOIN_CLAUSE_ISBN = "JOIN books_genres ON genres.id = books_genres.genre_id JOIN books ON books_genres.book_id = books.id";
    private static final String JOIN_CLAUSE_ID_BOOK = "JOIN books_genres ON genres.id = books_genres.genre_id";

    @Autowired
    public GenreRepositoryJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dynamicSQLBuilder = new DynamicSQLBuilder<>(
                jdbcTemplate,
                namedParameterJdbcTemplate,
                TABLE_NAME,
                List.of(ID_COLUMN),
                new GenreRowMapper()
        );
    }

    @Override
    public List<Genre> getByIsbnBook(String isbn) {
        return dynamicSQLBuilder.findByCriteria(SELECT_GENRES, JOIN_CLAUSE_ISBN, Collections.singletonMap(ISBN_COLUMN, isbn));
    }

    @Override
    public List<Genre> getByIdBook(long idBook) {
        return dynamicSQLBuilder.findByCriteria(SELECT_GENRES, JOIN_CLAUSE_ID_BOOK, Collections.singletonMap(ID_COLUMN, idBook));
    }

    @Override
    public List<Genre> findAllById(Long[] ids) {
        return dynamicSQLBuilder.findByIds(List.of(ids));
    }
}