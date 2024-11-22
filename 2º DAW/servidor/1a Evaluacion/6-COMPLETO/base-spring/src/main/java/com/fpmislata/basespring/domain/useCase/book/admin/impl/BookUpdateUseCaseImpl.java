package com.fpmislata.basespring.domain.useCase.book.admin.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.useCase.book.admin.BookUpdateUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class BookUpdateUseCaseImpl implements BookUpdateUseCase {
    private final BookService bookService;
    private final BookRelationHandlerImpl bookRelationHandler;

    @Override
    public void execute(Book book) {
        if (bookService.getById(book.getId()).isEmpty()) {
            throw new ResourceNotFoundException("Book with ID " + book.getId() + " not found");
        }
        bookRelationHandler.resolveRelations(book);
        bookService.save(book);
    }
}
