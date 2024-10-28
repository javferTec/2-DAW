package com.fpmislata.basespring.persistence.user.repository;

import com.fpmislata.basespring.domain.user.model.BookUser;

import java.util.List;
import java.util.Optional;

public interface BookUserRepository {
    List<BookUser> findAll();
    List<BookUser> findAll(int page, int size);
    int count();
    Optional<BookUser> findByIsbn(String isbn);
}
