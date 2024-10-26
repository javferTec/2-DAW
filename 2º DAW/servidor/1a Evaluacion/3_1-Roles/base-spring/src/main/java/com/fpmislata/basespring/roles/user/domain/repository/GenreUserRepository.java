package com.fpmislata.basespring.roles.user.domain.repository;

import com.fpmislata.basespring.roles.user.domain.model.GenreUser;

import java.util.List;

public interface GenreUserRepository {
    List<GenreUser> getByIsbnBook(String isbn);
}
