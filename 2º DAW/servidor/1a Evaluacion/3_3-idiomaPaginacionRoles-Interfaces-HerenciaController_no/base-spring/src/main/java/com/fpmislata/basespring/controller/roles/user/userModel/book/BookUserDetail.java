package com.fpmislata.basespring.controller.roles.user.userModel.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpmislata.basespring.controller.common.entity.model.publisher.PublisherCollection;
import com.fpmislata.basespring.controller.common.entity.model.author.AuthorCollection;

import java.math.BigDecimal;
import java.util.List;

public record BookUserDetail(
        String isbn,
        String title,
        BigDecimal price,
        float discount,
        String synopsis,
        String cover,
        List<String> genres,
        String category,
        @JsonProperty("publisher") PublisherCollection publisherCollection,
        @JsonProperty("authors") List<AuthorCollection> authorsCollection
) {
}
