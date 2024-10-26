package com.fpmislata.basespring.data;

import com.fpmislata.basespring.roles.user.domain.model.CategoryUser;

import java.util.List;

public class CategoryTestData {
    public List<CategoryUser> getCategories() {
        return List.of(
                new CategoryUser("Fantasía", "fantasia"),
                new CategoryUser("Ciencia Ficción", "ciencia-ficcion"),
                new CategoryUser("Terror", "terror"),
                new CategoryUser("Romántica", "romantica")
        );
    }
}
