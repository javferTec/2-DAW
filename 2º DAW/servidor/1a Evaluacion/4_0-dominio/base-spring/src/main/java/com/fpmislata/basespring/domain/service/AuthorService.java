package com.fpmislata.basespring.domain.service;

import com.fpmislata.basespring.domain.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getByIdBook(long idBook);

    List<Author> findAllById(List<Author> authors);
}
