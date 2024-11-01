package com.fpmislata.basespring.data;

import com.fpmislata.basespring.domain.useCase.book.roles.user.model.*;

import java.math.BigDecimal;
import java.util.List;

public class BookTestData {
    public static List<BookUser> getBooks() {
        List<PublisherUser> publisherUsers = new PublisherTestData().getPublishers();
        List<GenreUser> genreUsers = new GenreTestData().getGenres();
        List<CategoryUser> categories = new CategoryTestData().getCategories();
        List<AuthorUser> authorUsers = new AuthorTestData().getAuthors();

        return List.of(
                new BookUser(
                        "1111",
                        "Book 1",
                        "synopsis book 1",
                        new BigDecimal("12.30"),
                        10f,
                        "cover1.jpg",
                        publisherUsers.get(0),
                        categories.get(0),
                        List.of(authorUsers.get(0), authorUsers.get(1)),
                        List.of(genreUsers.get(0), genreUsers.get(1))
                ),
                new BookUser(
                        "2222",
                        "Book 2",
                        "synopsis book 2",
                        new BigDecimal("15.50"),
                        15f,
                        "cover2.jpg",
                        publisherUsers.get(1),
                        categories.get(1),
                        List.of(authorUsers.get(2)),
                        List.of(genreUsers.get(1), genreUsers.get(2))
                ),
                new BookUser(
                        "3333",
                        "Book 3",
                        "synopsis book 3",
                        new BigDecimal("20.00"),
                        20f,
                        "cover3.jpg",
                        publisherUsers.get(0),
                        categories.get(2),
                        List.of(authorUsers.get(0)),
                        List.of(genreUsers.get(2))
                ),
                new BookUser(
                        "4444",
                        "Book 4",
                        "synopsis book 4",
                        new BigDecimal("25.00"),
                        25f,
                        "cover4.jpg",
                        publisherUsers.get(1),
                        categories.get(0),
                        List.of(authorUsers.get(1)),
                        List.of(genreUsers.get(3))
                )
        );
    }
}
