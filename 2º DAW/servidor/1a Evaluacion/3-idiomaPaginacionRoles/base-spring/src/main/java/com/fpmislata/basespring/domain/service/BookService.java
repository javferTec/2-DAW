package com.fpmislata.basespring.domain.service;

import com.fpmislata.basespring.domain.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    List<Book> findAll(int page, int size);
    int count();
    Book findByIsbn(String isbn);
}
