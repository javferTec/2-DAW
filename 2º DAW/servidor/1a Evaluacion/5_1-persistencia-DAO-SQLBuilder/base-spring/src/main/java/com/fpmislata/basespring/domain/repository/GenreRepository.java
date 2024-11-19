package com.fpmislata.basespring.domain.repository;

import com.fpmislata.basespring.domain.model.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getByIsbnBook(String isbn);

    List<Genre> getByIdBook(long idBook);

    List<Genre> findAllById(Long[] ids);
}
