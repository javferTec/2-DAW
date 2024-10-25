package com.fpmislata.basespring.roles.user.domain.repository;

import com.fpmislata.basespring.roles.user.domain.model.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> getByIsbnBook(String isbn);
}
