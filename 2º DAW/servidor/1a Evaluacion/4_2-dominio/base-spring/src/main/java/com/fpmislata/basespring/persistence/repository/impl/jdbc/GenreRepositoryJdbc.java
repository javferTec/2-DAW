package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.domain.repository.GenreRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.JdbcOperations;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.SqlBuilder;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.GenreRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJdbc implements GenreRepository {

    private final JdbcOperations jdbcOperations;
    private final SqlBuilder sqlBuilder;
    private final GenreRowMapper genreRowMapper;

    // Constantes para nombres de tabla y columnas
    public static final String TABLE_NAME = "genres";
    public static final String JOIN_TABLE = "books_genres";
    public static final String BOOKS_TABLE = "books";
    public static final String ISBN_COLUMN = "isbn";
    public static final String ID_COLUMN = "id";
    public static final String GENRE_ID_COLUMN = "genre_id";

    @Override
    public List<Genre> getByIsbnBook(String isbn) {
        String sql = sqlBuilder.findByRelation(TABLE_NAME, JOIN_TABLE, BOOKS_TABLE, ISBN_COLUMN, GENRE_ID_COLUMN);
        return jdbcOperations.getAll(sql, new Object[]{isbn}, genreRowMapper);
    }

    @Override
    public List<Genre> getByIdBook(long idBook) {
        String sql = sqlBuilder.findByRelation(TABLE_NAME, JOIN_TABLE, BOOKS_TABLE, ID_COLUMN, GENRE_ID_COLUMN);
        return jdbcOperations.getAll(sql, new Object[]{idBook}, genreRowMapper);
    }

    @Override
    public List<Genre> findAllById(Long[] ids) {
        String sql = sqlBuilder.findByColumn(TABLE_NAME, ID_COLUMN);
        Map<String, List<Long>> params = Map.of("ids", Arrays.asList(ids));
        return jdbcOperations.getAll(sql, params.values().toArray(), genreRowMapper);
    }
}