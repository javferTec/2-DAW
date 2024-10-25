package com.fpmislata.basespring.roles.user.domain.service;

import com.fpmislata.basespring.roles.user.domain.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll(int page, int size);
    int count();
    Book findByIsbn(String isbn);
}
