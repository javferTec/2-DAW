package com.fpmislata.basespring.domain.useCase.book.roles.admin.impl;

import com.fpmislata.basespring.common.annotation.DomainTransactional;
import com.fpmislata.basespring.common.annotation.DomainUseCase;
import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.service.GenreService;
import com.fpmislata.basespring.domain.useCase.book.roles.admin.BookInsertGenresUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class BookInsertGenresUseCaseImpl implements BookInsertGenresUseCase {

    private final BookService bookService;
    private final GenreService genreService;

    @Override
    public void execute(Integer id, List<Genre> genres) {
        Book book = bookService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book " + id + " not found"));

        genreService
                .findAllById(genres)
                .forEach(genre -> bookService.addGenre(book, genre));

        bookService.save(book);
    }
}