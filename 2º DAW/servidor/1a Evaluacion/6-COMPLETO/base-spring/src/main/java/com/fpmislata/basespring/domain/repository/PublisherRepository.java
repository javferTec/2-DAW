package com.fpmislata.basespring.domain.repository;

import com.fpmislata.basespring.domain.model.Publisher;

import java.util.Optional;

public interface PublisherRepository {
    Optional<Publisher> findById(Long id);
}