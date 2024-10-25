package com.fpmislata.basespring.roles.user.domain.repository;

import com.fpmislata.basespring.roles.user.domain.model.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getByIsbnBook(String isbn);
}
