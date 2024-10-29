package com.fpmislata.basespring.controller.common.userAdminMapper.author;

import com.fpmislata.basespring.controller.common.userAdminModel.author.AuthorCollection;
import com.fpmislata.basespring.domain.roles.user.model.AuthorUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorCollection toAuthorCollection(AuthorUser authorUser);
}
