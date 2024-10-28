package com.fpmislata.basespring.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUser {
    private String isbn;
    private String title;
    private String synopsis;
    private BigDecimal price;
    private float discount;
    private String cover;
    private PublisherUser publisherUser;
    private CategoryUser categoryUser;
    private List<AuthorUser> authorUsers;
    private List<GenreUser> genreUsers;
}
