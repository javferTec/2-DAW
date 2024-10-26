package com.fpmislata.basespring.roles.admin.domain.repository;

import com.fpmislata.basespring.roles.admin.domain.model.AuthorAdmin;

import java.util.List;

public interface AuthorAdminRepository {
    List<AuthorAdmin> getByIsbnBook(String isbn);
}
