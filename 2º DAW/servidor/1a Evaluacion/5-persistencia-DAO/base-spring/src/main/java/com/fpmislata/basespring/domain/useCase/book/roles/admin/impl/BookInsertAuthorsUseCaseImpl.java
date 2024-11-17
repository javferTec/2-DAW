package com.fpmislata.basespring.domain.useCase.book.roles.admin.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.service.AuthorService;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.useCase.book.roles.admin.BookInsertAuthorsUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class BookInsertAuthorsUseCaseImpl implements BookInsertAuthorsUseCase {

    private final BookService bookService;
    private final AuthorService authorService;

    @Override
    public void execute(Integer id, List<Author> authors) {
        Book book = bookService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book " + id + " not found"));

        authorService
                .findAllById(authors)
                .forEach(author -> bookService.addAuthor(book, author));

        bookService.save(book);
    }
}