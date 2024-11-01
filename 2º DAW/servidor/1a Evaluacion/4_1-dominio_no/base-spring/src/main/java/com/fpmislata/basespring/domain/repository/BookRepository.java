package com.fpmislata.basespring.domain.repository;

import com.fpmislata.basespring.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> getAll();

    List<Book> getAll(int page, int size);

    int count();

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findById(long id);

    void save(Book book);


}
