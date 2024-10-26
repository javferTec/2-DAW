package com.fpmislata.basespring.data;

import com.fpmislata.basespring.roles.user.domain.model.PublisherUser;

import java.util.List;

public class PublisherTestData {
    public List<PublisherUser> getPublishers() {
        return List.of(
                new PublisherUser(),
                new PublisherUser()
        );
    }
}
