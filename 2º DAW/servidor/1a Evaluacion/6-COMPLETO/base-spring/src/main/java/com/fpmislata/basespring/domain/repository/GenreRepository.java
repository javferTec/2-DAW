package com.fpmislata.basespring.domain.repository;

import com.fpmislata.basespring.domain.model.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getByIdBook(long idBook);

    List<Genre> getAllById(Long[] ids);
}
