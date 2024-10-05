package com.fpmislata.basespring.data;

import com.fpmislata.basespring.domain.model.Author;

import java.util.List;

public class AuthorTestData {
    public List<Author> getAuthors() {
        return List.of(
                new Author(1, "Author 1", "Nationality 1", "Biography 1", 1900, 2000),
                new Author(2, "Author 2", "Nationality 2", "Biography 2", 1910, 2010),
                new Author(3, "Author 3", "Nationality 3", "Biography 3", 1920, 2020),
                new Author(4, "Author 4", "Nationality 4", "Biography 4", 1930, 2030)
        );
    }
}
