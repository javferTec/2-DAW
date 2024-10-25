package com.fpmislata.basespring.data;

import com.fpmislata.basespring.roles.user.domain.model.Category;

import java.util.List;

public class CategoryTestData {
    public List<Category> getCategories() {
        return List.of(
                new Category(1, "Category 1", "slug 1"),
                new Category(2, "Category 2", "slug 2"),
                new Category(3, "Category 3", "slug 3"),
                new Category(4, "Category 4", "slug 4")
        );
    }
}
