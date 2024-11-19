package com.fpmislata.basespring.domain.service.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainService;
import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.domain.repository.GenreRepository;
import com.fpmislata.basespring.domain.service.GenreService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getByIdBook(long idBook) {
        return genreRepository.getByIdBook(idBook);
    }

    @Override
    public List<Genre> findAllById(List<Genre> genres) {
        List<Genre> foundGenres = genreRepository.findAllById(
                genres
                        .stream()
                        .map(Genre::getId)
                        .toArray(Long[]::new)
        );
        if (foundGenres.size() != genres.size()) {
            throw new ResourceNotFoundException("Some genres were not found");
        }
        return foundGenres;
    }
}