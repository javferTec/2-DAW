package com.fpmislata.basespring.domain.repository;

import com.fpmislata.basespring.domain.model.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> getByIdBook(long idBook);

    List<Author> getAllById(Long[] ids);
}
