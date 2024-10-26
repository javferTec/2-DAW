package com.fpmislata.basespring.roles.admin.domain.repository;

import com.fpmislata.basespring.roles.admin.domain.model.BookAdmin;

import java.util.List;
import java.util.Optional;

public interface BookAdminRepository {
    List<BookAdmin> findAll();
    List<BookAdmin> findAll(int page, int size);
    int count();
    Optional<BookAdmin> findByIsbn(String isbn);
}