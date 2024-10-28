package com.fpmislata.basespring.persistence.admin.repository;


import com.fpmislata.basespring.domain.admin.model.GenreAdmin;

import java.util.List;

public interface GenreAdminRepository {
    List<GenreAdmin> getByIsbnBook(String isbn);
}
