package com.fpmislata.basespring.domain.roles.user.service;

import com.fpmislata.basespring.domain.roles.user.model.BookUser;

import java.util.List;

public interface BookUserService {
    List<BookUser> getAll();

    List<BookUser> getAll(int page, int size);
    int count();
    BookUser findByIsbn(String isbn);
}
