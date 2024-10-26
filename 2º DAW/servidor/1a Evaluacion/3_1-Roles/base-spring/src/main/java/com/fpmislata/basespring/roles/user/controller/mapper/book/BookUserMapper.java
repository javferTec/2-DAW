package com.fpmislata.basespring.roles.user.controller.mapper.book;

import com.fpmislata.basespring.common.layer.controller.mapper.author.AuthorMapper;
import com.fpmislata.basespring.common.layer.controller.mapper.genre.GenreMapper;
import com.fpmislata.basespring.common.layer.controller.mapper.publisher.PublisherMapper;
import com.fpmislata.basespring.roles.user.controller.model.book.BookUserCollection;
import com.fpmislata.basespring.roles.user.controller.model.book.BookUserDetail;
import com.fpmislata.basespring.roles.user.domain.model.BookUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PublisherMapper.class, AuthorMapper.class, GenreMapper.class})
public interface BookUserMapper {

    BookUserMapper INSTANCE = Mappers.getMapper(BookUserMapper.class);

    BookUserCollection toBookCollection(BookUser bookUser);

    @Mapping(target ="publisherCollection", source = "publisherUser")
    @Mapping(target="authorsCollection", source = "authorUsers")
    @Mapping(target = "category", source = "categoryUser.name")
    @Mapping(target = "genres", source = "genreUsers")
    BookUserDetail toBookDetail(BookUser bookUser);

}
