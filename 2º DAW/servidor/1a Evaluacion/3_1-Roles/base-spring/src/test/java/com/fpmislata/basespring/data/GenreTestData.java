package com.fpmislata.basespring.data;

import com.fpmislata.basespring.roles.user.domain.model.GenreUser;

import java.util.List;

public class GenreTestData {
    public List<GenreUser> getGenres() {
        return List.of(
                new GenreUser("Fantasía", "fantasia"),
                new GenreUser("Ciencia Ficción", "ciencia-ficcion"),
                new GenreUser("Terror", "terror")
        );
    }
}
