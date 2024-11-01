package com.fpmislata.basespring.domain.useCase.book.roles.user;

import com.fpmislata.basespring.domain.useCase.book.roles.user.model.BookUser;

public interface BookFindByIsbnUserUseCase {
    BookUser execute(String isbn);
}