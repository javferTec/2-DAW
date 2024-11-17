package com.fpmislata.basespring.domain.useCase.book.roles.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherUser {
    private String name;
    private String slug;
}