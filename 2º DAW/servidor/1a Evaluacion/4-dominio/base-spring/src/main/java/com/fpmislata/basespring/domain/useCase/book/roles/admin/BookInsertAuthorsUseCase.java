package com.fpmislata.basespring.domain.useCase.book.roles.admin;

import com.fpmislata.basespring.domain.model.Author;

import java.util.List;

public interface BookInsertAuthorsUseCase {
    void execute(Integer id, List<Author> authors);
}