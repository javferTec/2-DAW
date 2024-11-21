package com.fpmislata.basespring.controller.user.userModel.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpmislata.basespring.common.annotation.controller.MappedField;
import com.fpmislata.basespring.controller.common.entity.model.author.AuthorCollection;
import com.fpmislata.basespring.controller.common.entity.model.publisher.PublisherCollection;

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

        @MappedField("publisher")
        @JsonProperty("publisher") PublisherCollection publisherCollection,

        @MappedField("authors")
        @JsonProperty("authors") List<AuthorCollection> authorsCollection
) {
}
