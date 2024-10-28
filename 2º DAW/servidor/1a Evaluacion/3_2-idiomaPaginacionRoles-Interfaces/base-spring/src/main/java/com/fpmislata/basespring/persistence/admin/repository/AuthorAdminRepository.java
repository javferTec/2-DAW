package com.fpmislata.basespring.persistence.admin.repository;


import com.fpmislata.basespring.domain.admin.model.AuthorAdmin;

import java.util.List;

public interface AuthorAdminRepository {
    List<AuthorAdmin> getByIsbnBook(String isbn);
}
