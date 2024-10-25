package com.fpmislata.basespring.roles.user.domain.repository;

import com.fpmislata.basespring.roles.user.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    List<Book> findAll(int page, int size);
    int count();
    Optional<Book> findByIsbn(String isbn);
}
