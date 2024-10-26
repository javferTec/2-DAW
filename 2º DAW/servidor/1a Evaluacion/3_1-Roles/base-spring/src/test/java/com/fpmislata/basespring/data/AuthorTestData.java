package com.fpmislata.basespring.data;

import com.fpmislata.basespring.roles.user.domain.model.AuthorUser;

import java.util.List;

public class AuthorTestData {
    public List<AuthorUser> getAuthors() {
        return List.of(
                new AuthorUser(1, "Author 1", "Nationality 1", "Biography 1", 1900, 2000),
                new AuthorUser(2, "Author 2", "Nationality 2", "Biography 2", 1910, 2010),
                new AuthorUser(3, "Author 3", "Nationality 3", "Biography 3", 1920, 2020),
                new AuthorUser(4, "Author 4", "Nationality 4", "Biography 4", 1930, 2030)
        );
    }
}
