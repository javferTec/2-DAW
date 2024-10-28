package com.fpmislata.basespring.domain.user.service;

import com.fpmislata.basespring.domain.user.model.BookUser;

import java.util.List;

public interface BookUserService {
    List<BookUser> findAll();

    List<BookUser> findAll(int page, int size);
    int count();
    BookUser findByIsbn(String isbn);
}
