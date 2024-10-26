package com.fpmislata.basespring.roles.user.domain.repository;

import com.fpmislata.basespring.roles.user.domain.model.AuthorUser;

import java.util.List;

public interface AuthorUserRepository {
    List<AuthorUser> getByIsbnBook(String isbn);
}
