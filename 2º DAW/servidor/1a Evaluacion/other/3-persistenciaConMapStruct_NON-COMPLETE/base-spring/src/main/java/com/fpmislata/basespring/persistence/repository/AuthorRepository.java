package com.fpmislata.basespring.persistence.repository;

import com.fpmislata.basespring.domain.model.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> getByIsbnBook(String isbn);
}
