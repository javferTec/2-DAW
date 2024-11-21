package com.fpmislata.basespring.domain.useCase.book.admin.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.common.exception.ResourceAlreadyExistsException;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.useCase.book.admin.BookInsertUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class BookInsertUseCaseImpl implements BookInsertUseCase {

    private final BookService bookService;
    private final BookRelationHandlerImpl bookRelationHandler;

    @Override
    public void execute(Book book) {
        if (bookService.getByIsbn(book.getIsbn()).isPresent()) {
            throw new ResourceAlreadyExistsException("Book with ISBN " + book.getIsbn() + " already exists");
        }

        bookRelationHandler.resolveRelations(book);
        bookService.save(book);
    }
}