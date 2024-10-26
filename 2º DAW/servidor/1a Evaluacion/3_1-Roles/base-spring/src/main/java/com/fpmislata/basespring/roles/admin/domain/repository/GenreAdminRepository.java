package com.fpmislata.basespring.roles.admin.domain.repository;

import com.fpmislata.basespring.roles.admin.domain.model.GenreAdmin;

import java.util.List;

public interface GenreAdminRepository {
    List<GenreAdmin> getByIsbnBook(String isbn);
}
