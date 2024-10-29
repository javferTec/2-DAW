package com.fpmislata.basespring.domain.admin.service;


import com.fpmislata.basespring.domain.admin.model.BookAdmin;

import java.util.List;

public interface BookAdminService {
    List<BookAdmin> getAll();
    List<BookAdmin> getAll(int page, int size);
    int count();
    BookAdmin findByIsbn(String isbn);
}
