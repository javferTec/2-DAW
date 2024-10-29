package com.fpmislata.basespring.data;

import com.fpmislata.basespring.domain.roles.user.model.GenreUser;

import java.util.List;

public class GenreTestData {
    public List<GenreUser> getGenres() {
        return List.of(
                new GenreUser(1, "Genre 1", "slug 1"),
                new GenreUser(2, "Genre 2", "slug 2"),
                new GenreUser(3, "Genre 3", "slug 3"),
                new GenreUser(4, "Genre 4", "slug 4")
        );
    }
}
