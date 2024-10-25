package com.fpmislata.basespring.roles.user.controller.webMapper.author;

import com.fpmislata.basespring.roles.user.controller.webModel.author.AuthorCollection;
import com.fpmislata.basespring.roles.user.domain.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorCollection toAuthorCollection(Author author);
}
