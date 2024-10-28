package com.fpmislata.basespring.persistence.user.repository;

import com.fpmislata.basespring.domain.user.model.AuthorUser;

import java.util.List;

public interface AuthorUserRepository {
    List<AuthorUser> getByIsbnBook(String isbn);
}
