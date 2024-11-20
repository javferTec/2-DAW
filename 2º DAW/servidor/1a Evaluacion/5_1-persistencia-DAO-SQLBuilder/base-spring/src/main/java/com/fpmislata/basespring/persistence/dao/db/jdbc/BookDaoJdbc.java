package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.persistence.dao.db.BookDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoJdbc implements BookDaoDb {
    private final JdbcTemplate jdbcTemplate;
    private final GenericRowMapper<Book> bookMapper;

    public BookDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookMapper = new GenericRowMapper<>(Book.class, jdbcTemplate);
    }

    @Override
    public List<Book> getAll() {
        String sql = """
                   SELECT * FROM books
                """;
        return jdbcTemplate.query(sql, bookMapper);
    }

    @Override
    public List<Book> getAll(int page, int size) {
        String sql = "SELECT * FROM books LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, bookMapper);
    }

    @Override
    public int count() {
        String sql = """
                   SELECT COUNT(*) FROM books
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        String sql = """
                     SELECT * FROM books
                     LEFT JOIN categories ON books.category_id = categories.id
                     LEFT JOIN publishers ON books.publisher_id = publishers.id
                     WHERE books.isbn = ?
                """;
        try {
            Book book = jdbcTemplate.queryForObject(sql, bookMapper, isbn);
            return Optional.of(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> getById(long id) {
        String sql = """
                     SELECT * FROM books
                     LEFT JOIN categories ON books.category_id = categories.id
                     LEFT JOIN publishers ON books.publisher_id = publishers.id
                     WHERE books.id = ?
                """;
        try {
            Book book = jdbcTemplate.queryForObject(sql, bookMapper, id);
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Book book) {
        String sql = """
                    UPDATE books
                    SET isbn = ?,
                        title_es = ?,
                        title_en = ?,
                        synopsis_es = ?,
                        synopsis_en = ?,
                        price = ?,
                        discount = ?,
                        cover = ?,
                        publisher_id = ?,
                        category_id = ?
                    WHERE id = ?
                """;
        jdbcTemplate.update(
                sql,
                book.getIsbn(),
                book.getTitleEs(),
                book.getTitleEn(),
                book.getSynopsisEs(),
                book.getSynopsisEn(),
                book.getPrice(),
                book.getDiscount(),
                book.getCover(),
                book.getPublisher().getId(),
                book.getCategory().getId(),
                book.getId()
        );
    }

    @Override
    public void delete(long id) {
        //TODO: Implementar borrar libro
    }

    @Override
    public long insert(Book book) {
        String sql = """
                    INSERT INTO books(
                      isbn, 
                      title_es, 
                      title_en, 
                      synopsis_es, 
                      synopsis_en, 
                      price, 
                      discount, 
                      cover, 
                      publisher_id, 
                      category_id)
                    VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitleEs());
            ps.setString(3, book.getTitleEn());
            ps.setString(4, book.getSynopsisEs());
            ps.setString(5, book.getSynopsisEn());
            ps.setBigDecimal(6, book.getPrice());
            ps.setFloat(7, book.getDiscount());
            ps.setString(8, book.getCover());
            ps.setLong(9, book.getPublisher().getId());
            ps.setLong(10, book.getCategory().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue(); // Devuelve el id generado
    }

    @Override
    public void deleteAuthors(long id) {
        String sql = """
                    DELETE FROM books_authors
                    WHERE book_id = ?
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void insertAuthors(long id, List<Author> authors) {
        String sql = """
                    INSERT INTO books_authors(book_id, author_id)
                    VALUES (?, ?)
                """;
        authors.stream().forEach(a -> jdbcTemplate.update(sql, id, a.getId()));
    }

    @Override
    public void deleteGenres(long id) {
        String sql = """
                    DELETE FROM books_genres
                    WHERE book_id = ?
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void insertGenres(long id, List<Genre> genres) {
        String sql = """
                    INSERT INTO books_genres(book_id, genre_id)
                    VALUES(?, ?)
                """;
        genres.stream().forEach(g -> jdbcTemplate.update(sql, id, g.getId()));
    }
}
