package com.fpmislata.basespring.domain.service.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainService;
import com.fpmislata.basespring.domain.model.Publisher;
import com.fpmislata.basespring.domain.repository.PublisherRepository;
import com.fpmislata.basespring.domain.service.PublisherService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public Optional<Publisher> getById(Long id) {
        return publisherRepository.getById(id);
    }
}
