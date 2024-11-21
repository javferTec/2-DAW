package com.fpmislata.basespring.controller.user.userMapper.book;

import com.fpmislata.basespring.controller.common.entity.model.author.AuthorCollection;
import com.fpmislata.basespring.controller.common.entity.model.publisher.PublisherCollection;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserCollection;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserDetail;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.model.Genre;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookUserMapperManual {

    public static BookUserCollection toBookCollection(Book book) {
        return new BookUserCollection(
                book.getIsbn(),
                book.getTitle(),
                book.getPrice(),
                book.getDiscount(),
                book.getCover()
        );
    }

    public static BookUserDetail toBookDetail(Book book) {
        // Mapeo de géneros
        List<String> genres = book.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toList());

        // Mapeo de autores
        List<AuthorCollection> authorsCollection = book.getAuthors().stream()
                .map(author -> new AuthorCollection(author.getId(),
                                                           author.getName()))
                .collect(Collectors.toList());

        // Mapeo de Publisher
        PublisherCollection publisherCollection = new PublisherCollection(
                book.getId(),
                book.getPublisher().getName()
        );

        return new BookUserDetail(
                book.getIsbn(),
                book.getTitle(),
                book.getPrice(),
                book.getDiscount(),
                book.getSynopsis(),
                book.getCover(),
                genres,
                book.getCategory().getName(),
                publisherCollection,
                authorsCollection
        );
    }
}
