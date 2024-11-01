package com.fpmislata.basespring.data;

import com.fpmislata.basespring.domain.useCase.book.roles.user.model.PublisherUser;

import java.util.List;

public class PublisherTestData {
    public List<PublisherUser> getPublishers() {
        return List.of(
                new PublisherUser(1, "Publisher 1", "slug 1"),
                new PublisherUser(2, "Publisher 2", "slug 2"),
                new PublisherUser(3, "Publisher 3", "slug 3"),
                new PublisherUser(4, "Publisher 4", "slug 4")
        );
    }
}
