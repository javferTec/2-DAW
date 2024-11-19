package com.fpmislata.basespring.persistence.repository.impl;

import com.fpmislata.basespring.domain.model.Publisher;
import com.fpmislata.basespring.domain.repository.PublisherRepository;
import com.fpmislata.basespring.persistence.dao.db.PublisherDaoDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PublisherRepositoryJdbc implements PublisherRepository {

    private final PublisherDaoDb publisherDao;

    @Override
    public Optional<Publisher> findById(Long id) {
        return publisherDao.findById(id);
    }
}