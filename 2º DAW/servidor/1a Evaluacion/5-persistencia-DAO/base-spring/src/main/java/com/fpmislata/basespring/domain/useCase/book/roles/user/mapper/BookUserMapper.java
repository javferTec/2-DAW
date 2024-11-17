package com.fpmislata.basespring.domain.useCase.book.roles.user.mapper;

import com.fpmislata.basespring.controller.common.entity.mapper.genre.GenreMapper;
import com.fpmislata.basespring.controller.common.entity.mapper.publisher.PublisherMapper;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.useCase.book.roles.user.model.BookUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PublisherMapper.class, GenreMapper.class, AuthorUserMapper.class, CategoryUserMapper.class})
public interface BookUserMapper {

    BookUserMapper INSTANCE = Mappers.getMapper(BookUserMapper.class);

    BookUser toBookUser(Book book);

}