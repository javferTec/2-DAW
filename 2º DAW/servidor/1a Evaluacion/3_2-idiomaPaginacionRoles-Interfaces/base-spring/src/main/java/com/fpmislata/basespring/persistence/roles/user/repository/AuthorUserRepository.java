package com.fpmislata.basespring.persistence.roles.user.repository;

import com.fpmislata.basespring.domain.roles.user.model.AuthorUser;

import java.util.List;

public interface AuthorUserRepository {
    List<AuthorUser> getByIsbnBook(String isbn);
}
