package com.fpmislata.basespring.persistence.roles.user.repository;

import com.fpmislata.basespring.domain.roles.user.model.GenreUser;

import java.util.List;

public interface GenreUserRepository {
    List<GenreUser> getByIsbnBook(String isbn);
}
