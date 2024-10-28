package com.fpmislata.basespring.persistence.admin.repository;


import com.fpmislata.basespring.domain.admin.model.BookAdmin;

import java.util.List;
import java.util.Optional;

public interface BookAdminRepository {
    List<BookAdmin> findAll();
    List<BookAdmin> findAll(int page, int size);
    int count();
    Optional<BookAdmin> findByIsbn(String isbn);
}