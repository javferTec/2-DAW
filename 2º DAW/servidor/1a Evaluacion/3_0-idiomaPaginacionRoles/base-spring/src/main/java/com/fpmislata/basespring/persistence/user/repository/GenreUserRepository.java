package com.fpmislata.basespring.persistence.user.repository;

import com.fpmislata.basespring.domain.user.model.GenreUser;

import java.util.List;

public interface GenreUserRepository {
    List<GenreUser> getByIsbnBook(String isbn);
}
