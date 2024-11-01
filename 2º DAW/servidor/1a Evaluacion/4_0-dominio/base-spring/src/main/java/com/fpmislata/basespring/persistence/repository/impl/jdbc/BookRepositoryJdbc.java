package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.domain.repository.BookRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.JdbcOperations;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.SqlBuilder;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.BookRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {

    // Constantes para nombres de tabla y columnas
    private static final String TABLE_NAME = "books";
    private static final String ID_COLUMN = "id";
    private static final String ISBN_COLUMN = "isbn";
    private static final String TITLE_ES_COLUMN = "title_es";
    private static final String TITLE_EN_COLUMN = "title_en";
    private static final String SYNOPSIS_ES_COLUMN = "synopsis_es";
    private static final String SYNOPSIS_EN_COLUMN = "synopsis_en";
    private static final String PRICE_COLUMN = "price";
    private static final String DISCOUNT_COLUMN = "discount";
    private static final String COVER_COLUMN = "cover";
    private static final String PUBLISHER_ID_COLUMN = "publisher_id";
    private static final String CATEGORY_ID_COLUMN = "category_id";
    // Constantes para relaciones
    private static final String BOOKS_AUTHORS_TABLE = "books_authors";
    private static final String BOOKS_GENRES_TABLE = "books_genres";
    private static final String BOOK_ID_COLUMN = "book_id";
    private static final String AUTHOR_ID_COLUMN = "author_id";
    private static final String GENRE_ID_COLUMN = "genre_id";
    private static final String[] ALL_COLUMNS = {
            ISBN_COLUMN,
            TITLE_ES_COLUMN,
            TITLE_EN_COLUMN,
            SYNOPSIS_ES_COLUMN,
            SYNOPSIS_EN_COLUMN,
            PRICE_COLUMN,
            DISCOUNT_COLUMN,
            COVER_COLUMN,
            PUBLISHER_ID_COLUMN,
            CATEGORY_ID_COLUMN
    };
    private static final String[][] JOIN_CLAUSES = {
            {"categories", TABLE_NAME + "." + CATEGORY_ID_COLUMN, "categories.id"},
            {"publishers", TABLE_NAME + "." + PUBLISHER_ID_COLUMN, "publishers.id"}
    };
    private final JdbcOperations jdbcOperations;

    @Override
    public List<Book> getAll() {
        String sql = SqlBuilder.getAll(TABLE_NAME);
        return jdbcOperations.getAll(sql, new Object[]{}, new BookRowMapper());
    }

    @Override
    public List<Book> getAll(int page, int size) {
        String baseQuery = SqlBuilder.getAll(TABLE_NAME);
        String sql = SqlBuilder.paginatedQuery(baseQuery, page, size);
        return jdbcOperations.getAll(sql, new Object[]{}, new BookRowMapper());
    }

    @Override
    public int count() {
        String sql = SqlBuilder.count(TABLE_NAME);
        return jdbcOperations.count(sql);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        String sql = SqlBuilder.findWithJoins(TABLE_NAME, JOIN_CLAUSES, ISBN_COLUMN);
        return jdbcOperations.findByIsbn(sql, isbn, new BookRowMapper());
    }

    @Override
    public Optional<Book> findById(long id) {
        String sql = SqlBuilder.findWithJoins(TABLE_NAME, JOIN_CLAUSES, ID_COLUMN);
        return jdbcOperations.findById(sql, id, new BookRowMapper());
    }

    @Override
    public void save(Book book) {
        if (book.getId() != null) {
            update(book);
        } else {
            long id = insert(book);
            book.setId(id);
        }
        deleteAuthors(book.getId());
        insertAuthors(book.getId(), book.getAuthors());
        deleteGenres(book.getId());
        insertGenres(book.getId(), book.getGenres());
    }

    private void update(Book book) {
        String sql = SqlBuilder.update(TABLE_NAME, ALL_COLUMNS, ID_COLUMN);
        jdbcOperations.save(sql, new Object[]{
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
        });
    }

    private long insert(Book book) {
        String sql = SqlBuilder.insert(TABLE_NAME, ALL_COLUMNS);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.insertAndReturnId(sql, new Object[]{
                book.getIsbn(),
                book.getTitleEs(),
                book.getTitleEn(),
                book.getSynopsisEs(),
                book.getSynopsisEn(),
                book.getPrice(),
                book.getDiscount(),
                book.getCover(),
                book.getPublisher().getId(),
                book.getCategory().getId()
        });

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private void deleteAuthors(long id) {
        String sql = SqlBuilder.delete(BOOKS_AUTHORS_TABLE, BOOK_ID_COLUMN);
        jdbcOperations.delete(sql, new Object[]{id});
    }

    private void insertAuthors(long id, List<Author> authors) {
        String sql = SqlBuilder.insertManyToMany(BOOKS_AUTHORS_TABLE, BOOK_ID_COLUMN, AUTHOR_ID_COLUMN);
        authors.forEach(a -> jdbcOperations.save(sql, new Object[]{id, a.getId()}));
    }

    private void deleteGenres(long id) {
        String sql = SqlBuilder.delete(BOOKS_GENRES_TABLE, BOOK_ID_COLUMN);
        jdbcOperations.delete(sql, new Object[]{id});
    }

    private void insertGenres(long id, List<Genre> genres) {
        String sql = SqlBuilder.insertManyToMany(BOOKS_GENRES_TABLE, BOOK_ID_COLUMN, GENRE_ID_COLUMN);
        genres.forEach(g -> jdbcOperations.save(sql, new Object[]{id, g.getId()}));
    }
}
