package com.fpmislata.basespring.domain.service;

import com.fpmislata.basespring.domain.model.Publisher;

import java.util.Optional;

public interface PublisherService {
    Optional<Publisher> findById(Long id);
}