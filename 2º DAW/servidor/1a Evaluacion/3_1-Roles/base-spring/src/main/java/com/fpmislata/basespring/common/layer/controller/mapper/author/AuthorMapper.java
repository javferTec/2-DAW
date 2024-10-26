package com.fpmislata.basespring.common.layer.controller.mapper.author;

import com.fpmislata.basespring.common.layer.controller.model.author.AuthorCollection;
import com.fpmislata.basespring.roles.user.domain.model.AuthorUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorCollection toAuthorCollection(AuthorUser authorUser);
}
