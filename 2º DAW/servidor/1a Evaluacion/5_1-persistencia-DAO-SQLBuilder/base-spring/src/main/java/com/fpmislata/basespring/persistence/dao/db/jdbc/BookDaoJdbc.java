package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.persistence.dao.db.BookDaoDb;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Dao
public class BookDaoJdbc extends BaseDaoJdbc<Book> implements BookDaoDb {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoJdbc(DataSource dataSource) {
        super(Book.class, dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Book> getAll() {
        return super.getAll();
    }

    @Override
    public List<Book> getAll(int page, int size) {
        return super.getAll(page, size);
    }

    @Override
    public int count() {
        return super.count();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        Map<String, Object> criteria = Map.of("isbn", isbn);
        return super.getByCriteria(criteria).stream().findFirst();
    }

    @Override
    public Optional<Book> getById(long id) {
        return super.getById(id);
    }

    @Override
    public void update(Book book) {
        super.update(book);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public long insert(Book book) {
        return super.insert(book);
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
        authors.forEach(a -> jdbcTemplate.update(sql, id, a.getId()));
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
        genres.forEach(g -> jdbcTemplate.update(sql, id, g.getId()));
    }
}
