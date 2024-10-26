package com.fpmislata.basespring.roles.admin.domain.service;

import com.fpmislata.basespring.roles.admin.domain.model.BookAdmin;

import java.util.List;

public interface BookAdminService {
    List<BookAdmin> findAll();
    List<BookAdmin> findAll(int page, int size);
    int count();
    BookAdmin findByIsbn(String isbn);
}
