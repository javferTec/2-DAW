package com.fpmislata.basespring.roles.user.domain.repository;

import com.fpmislata.basespring.roles.user.domain.model.BookUser;

import java.util.List;
import java.util.Optional;

public interface BookUserRepository {
    List<BookUser> findAll();
    List<BookUser> findAll(int page, int size);
    int count();
    Optional<BookUser> findByIsbn(String isbn);
}
