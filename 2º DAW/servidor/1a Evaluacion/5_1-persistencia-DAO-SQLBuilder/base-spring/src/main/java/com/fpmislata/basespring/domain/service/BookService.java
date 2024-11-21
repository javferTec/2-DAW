package com.fpmislata.basespring.domain.service;

import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();

    List<Book> getAll(int page, int size);

    int count();

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findById(long id);

    void save(Book book);

    void addAuthor(Book book, Author author);

    void addGenre(Book book, Genre genre);

    void delete(long id);
}