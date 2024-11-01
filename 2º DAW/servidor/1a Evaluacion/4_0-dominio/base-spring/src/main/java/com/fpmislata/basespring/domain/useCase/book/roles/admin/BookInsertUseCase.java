package com.fpmislata.basespring.domain.useCase.book.roles.admin;

import com.fpmislata.basespring.domain.model.Book;

public interface BookInsertUseCase {
    void execute(Book book);
}