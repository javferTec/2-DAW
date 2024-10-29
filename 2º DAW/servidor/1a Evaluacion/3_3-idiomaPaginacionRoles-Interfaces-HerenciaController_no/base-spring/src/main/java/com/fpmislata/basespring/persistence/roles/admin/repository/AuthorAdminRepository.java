package com.fpmislata.basespring.persistence.roles.admin.repository;


import com.fpmislata.basespring.domain.roles.admin.model.AuthorAdmin;

import java.util.List;

public interface AuthorAdminRepository {
    List<AuthorAdmin> getByIsbnBook(String isbn);
}
