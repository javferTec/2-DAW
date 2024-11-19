package com.fpmislata.basespring.domain.useCase.book.common;

import com.fpmislata.basespring.domain.model.Book;

public interface BookFindByIsbnUseCase {
    Book execute(String isbn);
}