package com.fpmislata.basespring.controller.common.entity.mapper.author;

import com.fpmislata.basespring.controller.common.entity.model.author.AuthorCollection;
import com.fpmislata.basespring.domain.model.Author;import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorCollection toAuthorCollection(Author authorUser);
}
