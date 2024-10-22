package com.fpmislata.basespring.domain.service;

import com.fpmislata.basespring.domain.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findByIsbn(String isbn);
}
