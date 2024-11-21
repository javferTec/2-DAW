package com.fpmislata.basespring.controller.user.userMapper.book;

import com.fpmislata.basespring.common.annotation.common.Mapper;
import com.fpmislata.basespring.controller.common.entity.model.author.AuthorCollection;
import com.fpmislata.basespring.controller.common.entity.model.publisher.PublisherCollection;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserDetail;
import com.fpmislata.basespring.domain.model.Book;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class BookUserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static BookUserDetail toBookDetail(Book book) {
        // Mapear el libro a BookUserDetail
        BookUserDetail bookUserDetail = modelMapper.map(book, BookUserDetail.class);

        // Mapea la editorial
        if (book.getPublisher() != null) {
            bookUserDetail.setPublisherCollection(new PublisherCollection(
                    book.getPublisher().getId(),
                    book.getPublisher().getName()
            ));
        }

        // Mapea los autores
        if (book.getAuthors() != null) {
            List<AuthorCollection> authorsCollection = book.getAuthors().stream()
                    .map(author -> new AuthorCollection(author.getId(), author.getName()))
                    .collect(Collectors.toList());
            bookUserDetail.setAuthorsCollection(authorsCollection);
        }

        // Mapea los géneros
        if (book.getGenres() != null) {
            List<String> genres = book.getGenres().stream()
                    .map(genre -> genre.getName())
                    .collect(Collectors.toList());
            bookUserDetail.setGenres(genres);
        }

        // Mapea la categoría
        if (book.getCategory() != null) {
            bookUserDetail.setCategory(book.getCategory().getName());
        }

        return bookUserDetail;
    }
}
