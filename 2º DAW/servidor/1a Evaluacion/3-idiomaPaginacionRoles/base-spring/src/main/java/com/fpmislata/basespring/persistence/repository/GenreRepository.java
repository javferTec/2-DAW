package com.fpmislata.basespring.persistence.repository;

import com.fpmislata.basespring.domain.model.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getByIsbnBook(String isbn);
}
