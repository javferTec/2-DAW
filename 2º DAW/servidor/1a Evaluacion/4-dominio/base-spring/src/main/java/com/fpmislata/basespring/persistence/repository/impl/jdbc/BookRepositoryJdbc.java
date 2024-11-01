package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.domain.repository.AuthorRepository;
import com.fpmislata.basespring.domain.repository.BookRepository;
import com.fpmislata.basespring.domain.repository.GenreRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.DynamicSQLBuilder;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.BookRowMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final DynamicSQLBuilder<Book> dynamicSQLBuilder;

    private static final String TABLE_NAME = "books";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ISBN = "isbn";
    private static final String COLUMN_TITLE_ES = "title_es";
    private static final String COLUMN_TITLE_EN = "title_en";
    private static final String COLUMN_SYNOPSIS_ES = "synopsis_es";
    private static final String COLUMN_SYNOPSIS_EN = "synopsis_en";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DISCOUNT = "discount";
    private static final String COLUMN_COVER = "cover";
    private static final String COLUMN_PUBLISHER_ID = "publisher_id";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String RELATION_TABLE_AUTHORS = "books_authors";
    private static final String RELATION_TABLE_GENRES = "books_genres";
    private static final String RELATION_COLUMN_BOOK_ID = "book_id";
    private static final String RELATION_COLUMN_AUTHOR_ID = "author_id";
    private static final String RELATION_COLUMN_GENRE_ID = "genre_id";

    @Autowired
    public BookRepositoryJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.dynamicSQLBuilder = new DynamicSQLBuilder<>(
                jdbcTemplate,
                namedParameterJdbcTemplate,
                TABLE_NAME,
                List.of(COLUMN_ID),
                new BookRowMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return dynamicSQLBuilder.findAll();
    }

    @Override
    public List<Book> getAll(int page, int size) {
        return dynamicSQLBuilder.findAllWithPagination(page, size);
    }

    @Override
    public int count() {
        return dynamicSQLBuilder.count();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return dynamicSQLBuilder.findByColumn(COLUMN_ISBN, isbn);
    }

    @Override
    public Optional<Book> findById(long id) {
        return dynamicSQLBuilder.findById(COLUMN_ID, id);
    }

    @Override
    public void save(Book book) {
        Map<String, Object> fields = Map.of(
                COLUMN_ISBN, book.getIsbn(),
                COLUMN_TITLE_ES, book.getTitleEs(),
                COLUMN_TITLE_EN, book.getTitleEn(),
                COLUMN_SYNOPSIS_ES, book.getSynopsisEs(),
                COLUMN_SYNOPSIS_EN, book.getSynopsisEn(),
                COLUMN_PRICE, book.getPrice(),
                COLUMN_DISCOUNT, book.getDiscount(),
                COLUMN_COVER, book.getCover(),
                COLUMN_PUBLISHER_ID, book.getPublisher().getId(),
                COLUMN_CATEGORY_ID, book.getCategory().getId()
        );

        dynamicSQLBuilder.save(fields, book.getId());
        updateRelations(book);
    }

    private void updateRelations(Book book) {
        deleteAuthors(book.getId());
        insertAuthors(book.getId(), book.getAuthors());
        deleteGenres(book.getId());
        insertGenres(book.getId(), book.getGenres());
    }

    private void deleteAuthors(long id) {
        dynamicSQLBuilder.deleteById(RELATION_COLUMN_BOOK_ID, id);
    }

    private void insertAuthors(long id, List<Author> authors) {
        authors.forEach(author -> {
            Map<String, Object> fields = Map.of(RELATION_COLUMN_BOOK_ID, id, RELATION_COLUMN_AUTHOR_ID, author.getId());
            dynamicSQLBuilder.insertIntoRelation(RELATION_TABLE_AUTHORS, fields);
        });
    }

    private void deleteGenres(long id) {
        dynamicSQLBuilder.deleteById(RELATION_COLUMN_BOOK_ID, id);
    }

    private void insertGenres(long id, List<Genre> genres) {
        genres.forEach(genre -> {
            Map<String, Object> fields = Map.of(RELATION_COLUMN_BOOK_ID, id, RELATION_COLUMN_GENRE_ID, genre.getId());
            dynamicSQLBuilder.insertIntoRelation(RELATION_TABLE_GENRES, fields);
        });
    }
}
