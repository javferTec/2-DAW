package com.fpmislata.basespring.domain.useCase.book.admin.impl;

import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.service.AuthorService;
import com.fpmislata.basespring.domain.service.CategoryService;
import com.fpmislata.basespring.domain.service.GenreService;
import com.fpmislata.basespring.domain.service.PublisherService;
import com.fpmislata.basespring.domain.useCase.book.admin.BookRelationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookRelationHandlerImpl implements BookRelationHandler {

    private final PublisherService publisherService;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public void resolveRelations(Book book) {
        book.setPublisher(publisherService
                .getById(book.getPublisher().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher " + book.getPublisher().getName() + " not found")));

        book.setCategory(categoryService
                .getById(book.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category " + book.getCategory().getId() + " not found")));

        book.setAuthors(authorService.getAllById(book.getAuthors()));

        book.setGenres(genreService.getAllById(book.getGenres()));
    }
}
