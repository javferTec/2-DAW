package com.fpmislata.basespring.persistence.repository;

import com.fpmislata.basespring.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findByIsbn(String isbn);
}
