package com.fpmislata.basespring.persistence.roles.admin.repository;


import com.fpmislata.basespring.domain.roles.admin.model.GenreAdmin;

import java.util.List;

public interface GenreAdminRepository {
    List<GenreAdmin> getByIsbnBook(String isbn);
}
