package com.fpmislata.basespring.data;

import com.fpmislata.basespring.domain.user.model.CategoryUser;

import java.util.List;

public class CategoryTestData {
    public List<CategoryUser> getCategories() {
        return List.of(
                new CategoryUser(1, "Category 1", "slug 1"),
                new CategoryUser(2, "Category 2", "slug 2"),
                new CategoryUser(3, "Category 3", "slug 3"),
                new CategoryUser(4, "Category 4", "slug 4")
        );
    }
}
