package com.fpmislata.basespring.controller.user.userMapper.book;

import com.fpmislata.basespring.controller.common.entity.mapper.author.AuthorMapper;
import com.fpmislata.basespring.controller.common.entity.mapper.genre.GenreMapper;
import com.fpmislata.basespring.controller.common.entity.mapper.publisher.PublisherMapper;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserCollection;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserDetail;
import com.fpmislata.basespring.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PublisherMapper.class, AuthorMapper.class, GenreMapper.class})
public interface BookUserMapper {

    BookUserMapper INSTANCE = Mappers.getMapper(BookUserMapper.class);

    BookUserCollection toBookCollection(Book bookUser);

    @Mapping(target = "publisherCollection", source = "publisher")
    @Mapping(target = "authorsCollection", source = "authors")
    @Mapping(target = "category", source = "category.name")
    @Mapping(target = "genres", source = "genres")
    BookUserDetail toBookDetail(Book bookUser);

}
