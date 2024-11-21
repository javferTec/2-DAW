package com.fpmislata.basespring.domain.service.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainService;
import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.repository.AuthorRepository;
import com.fpmislata.basespring.domain.service.AuthorService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getByIdBook(long idBook) {
        return authorRepository.getByIdBook(idBook);
    }

    @Override
    public List<Author> findAllById(List<Author> authors) {
        List<Author> foundAuthors = authorRepository.findAllById(
                authors
                        .stream()
                        .map(Author::getId)
                        .toArray(Long[]::new)
        );
        if (foundAuthors.size() != authors.size()) {
            throw new ResourceNotFoundException("Some authors were not found");
        }
        return foundAuthors;
    }
}