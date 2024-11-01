package com.fpmislata.basespring.domain.useCase.book.roles.admin;

import com.fpmislata.basespring.domain.model.Genre;

import java.util.List;

public interface BookInsertGenresUseCase {
    void execute(Integer id, List<Genre> genres);
}