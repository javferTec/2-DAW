package com.fpmislata.basespring.data;

import com.fpmislata.basespring.domain.model.Genre;

import java.util.List;

public class GenreTestData {
    public List<Genre> getGenres() {
        return List.of(
                new Genre(1, "Genre 1", "slug 1"),
                new Genre(2, "Genre 2", "slug 2"),
                new Genre(3, "Genre 3", "slug 3"),
                new Genre(4, "Genre 4", "slug 4")
        );
    }
}
