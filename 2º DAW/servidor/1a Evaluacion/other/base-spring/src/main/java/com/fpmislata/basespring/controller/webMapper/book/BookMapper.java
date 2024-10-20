package com.fpmislata.basespring.controller.webMapper.book;

import com.fpmislata.basespring.controller.webMapper.author.AuthorMapper;
import com.fpmislata.basespring.controller.webMapper.genre.GenreMapper;
import com.fpmislata.basespring.controller.webMapper.publisher.PublisherMapper;
import com.fpmislata.basespring.controller.webModel.book.BookCollection;
import com.fpmislata.basespring.controller.webModel.book.BookDetail;
import com.fpmislata.basespring.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PublisherMapper.class, AuthorMapper.class, GenreMapper.class})
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookCollection toBookCollection(Book book);

    @Mapping(target ="publisherCollection", source = "publisher")
    @Mapping(target="authorsCollection", source = "authors")
    @Mapping(target = "category", source = "category.name")
    @Mapping(target = "genres", source = "genres")
    BookDetail toBookDetail(Book book);

}
