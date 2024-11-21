package com.fpmislata.basespring.persistence.repository.impl;

import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.repository.BookRepository;
import com.fpmislata.basespring.persistence.dao.cache.BookDaoCache;
import com.fpmislata.basespring.persistence.dao.db.BookDaoDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {

    private final BookDaoDb bookDaoJdbc;
    private final BookDaoCache bookDaoCache;

    @Override
    public List<Book> getAll() {
        return bookDaoJdbc.getAll();
    }

    @Override
    public List<Book> getAll(int page, int size) {
        return bookDaoJdbc.getAll(page, size);
    }

    @Override
    public int count() {
        return bookDaoJdbc.count();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookDaoCache.findByIsbn(isbn).or(
                () -> {
                    System.out.println("Retrieved from db: " + isbn);
                    Optional<Book> book = bookDaoJdbc.findByIsbn(isbn);
                    book.ifPresent(bookDaoCache::save);
                    return book;
                }
        );
    }

    @Override
    public Optional<Book> findById(long id) {
        return bookDaoJdbc.getById(id);
    }

    @Override
    public void save(Book book) {
        //Si el id existe, actualizar, si no, instalar
        if(book.getId() != null) {
            bookDaoJdbc.update(book);
        } else {
            long id = bookDaoJdbc.insert(book);
            book.setId(id);
        }
        /*bookDaoJdbc.deleteAuthors(book.getId());
        bookDaoJdbc.insertAuthors(book.getId(), book.getAuthors());
        bookDaoJdbc.deleteGenres(book.getId());
        bookDaoJdbc.insertGenres(book.getId(), book.getGenres());*/
    }
}
