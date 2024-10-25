package com.fpmislata.basespring.data;

import com.fpmislata.basespring.roles.user.domain.model.Publisher;

import java.util.List;

public class PublisherTestData {
    public List<Publisher> getPublishers() {
        return List.of(
                new Publisher(1, "Publisher 1", "slug 1"),
                new Publisher(2, "Publisher 2", "slug 2"),
                new Publisher(3, "Publisher 3", "slug 3"),
                new Publisher(4, "Publisher 4", "slug 4")
        );
    }
}
