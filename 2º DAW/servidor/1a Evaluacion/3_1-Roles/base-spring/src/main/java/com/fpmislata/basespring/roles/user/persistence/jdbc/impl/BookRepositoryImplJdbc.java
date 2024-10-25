package com.fpmislata.basespring.roles.user.persistence.jdbc.impl;

import com.fpmislata.basespring.roles.user.domain.model.Book;
import com.fpmislata.basespring.roles.user.domain.repository.AuthorRepository;
import com.fpmislata.basespring.roles.user.domain.repository.BookRepository;
import com.fpmislata.basespring.roles.user.domain.repository.GenreRepository;
import com.fpmislata.basespring.roles.user.persistence.jdbc.impl.mapper.BookRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImplJdbc implements BookRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    @Override
    public List<Book> findAll(int page, int size) {
        String sql = "SELECT * FROM books LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new BookRowMapper(), size, page * size);
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM books";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return (count != null) ? count : 0;
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
            Book book = jdbcTemplate.queryForObject(sql, new BookRowMapper(), isbn);
            assert book != null;
            book.setAuthors(authorRepository.getByIsbnBook(isbn));
            book.setGenres(genreRepository.getByIsbnBook(isbn));
            return Optional.of(book);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
