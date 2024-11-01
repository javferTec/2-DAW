package com.fpmislata.basespring.domain.service;

import com.fpmislata.basespring.domain.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getByIdBook(long idBook);

    List<Genre> findAllById(List<Genre> genres);
}