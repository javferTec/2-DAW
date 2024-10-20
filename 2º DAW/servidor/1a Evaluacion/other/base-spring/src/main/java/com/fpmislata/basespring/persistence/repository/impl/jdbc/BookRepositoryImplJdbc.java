package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.persistence.repository.AuthorRepository;
import com.fpmislata.basespring.persistence.repository.BookRepository;
import com.fpmislata.basespring.persistence.repository.GenreRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.BookRowMapper;
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
        String sql = "SELECT * FROM books LEFT JOIN categories ON books.category_id = categories.id LEFT JOIN publishers ON books.publisher_id = publishers.id";
        return jdbcTemplate.query(sql, new BookRowMapper());
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
