package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.persistence.dao.db.AuthorDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.factory.GenericRowMapperFactory;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Dao
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDaoDb {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GenericRowMapperFactory rowMapperFactory;
    private GenericRowMapper<Author> authorRowMapper;

    @PostConstruct
    public void init() {
        this.authorRowMapper = rowMapperFactory.createRowMapper(Author.class);
    }

    @Override
    public List<Author> getByIsbnBook(String isbn) {
        String sql = """
                SELECT authors.* FROM authors
                JOIN books_authors ON authors.id = books_authors.author_id
                JOIN books ON books_authors.book_id = books.id
                AND books.isbn = ?
           """;
        return jdbcTemplate.query(sql, authorRowMapper, isbn);
    }

    @Override
    public List<Author> getByIdBook(long idBook) {
        String sql = """
                SELECT authors.* FROM authors
                JOIN books_authors ON authors.id = books_authors.author_id
                AND books_authors.book_id = ?
           """;
        return jdbcTemplate.query(sql, authorRowMapper, idBook);
    }

    @Override
    public List<Author> findAllById(Long[] ids) {
        String sql = """
               SELECT authors.* FROM authors
               WHERE id IN (:ids)   
           """;
        Map<String, List<Long>> params = Map.of("ids", Arrays.asList(ids));
        return namedParameterJdbcTemplate.query(sql, params, authorRowMapper);
    }


    @Override
    public List<Author> getAll() {
        //TODO: Implementar obtener todos los autores
        return List.of();
    }

    @Override
    public List<Author> getAll(int page, int size) {
        //TODO: Implementar obtener todos los autores paginados
        return List.of();
    }

    @Override
    public Optional<Author> findById(long id) {
        //TODO: Implementar obtener un autor por id
        return Optional.empty();
    }

    @Override
    public long insert(Author author) {
        //TODO: Implementar insertar un autor
        return 0;
    }

    @Override
    public void update(Author author) {
        //TODO: Implementar actualizar un autor
    }

    @Override
    public void delete(long id) {
        //TODO: Implementar borrar un autor
    }

    @Override
    public int count() {
        return 0;
    }
}