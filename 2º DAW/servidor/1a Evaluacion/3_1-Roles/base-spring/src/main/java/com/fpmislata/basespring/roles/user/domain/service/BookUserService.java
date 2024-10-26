package com.fpmislata.basespring.roles.user.domain.service;

import com.fpmislata.basespring.roles.user.domain.model.BookUser;

import java.util.List;

public interface BookUserService {
    List<BookUser> findAll();
    List<BookUser> findAll(int page, int size);
    int count();
    BookUser findByIsbn(String isbn);
}
