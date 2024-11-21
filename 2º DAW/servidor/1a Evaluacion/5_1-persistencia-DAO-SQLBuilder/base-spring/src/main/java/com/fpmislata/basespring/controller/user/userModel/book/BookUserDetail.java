package com.fpmislata.basespring.controller.user.userModel.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpmislata.basespring.controller.common.entity.model.author.AuthorCollection;
import com.fpmislata.basespring.controller.common.entity.model.publisher.PublisherCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUserDetail {
    private String isbn;
    private String title;
    private BigDecimal price;
    private float discount;
    private String synopsis;
    private String cover;
    private List<String> genres;
    private String category;
    @JsonProperty("publisher")
    private PublisherCollection publisherCollection;
    @JsonProperty("authors")
    private List<AuthorCollection> authorsCollection;
}
