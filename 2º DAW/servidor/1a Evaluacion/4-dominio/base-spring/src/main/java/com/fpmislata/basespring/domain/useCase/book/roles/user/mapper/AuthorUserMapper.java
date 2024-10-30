package com.fpmislata.basespring.domain.useCase.book.roles.user.mapper;

import com.fpmislata.basespring.domain.useCase.book.roles.user.model.AuthorUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorUserMapper {

    AuthorUserMapper INSTANCE = Mappers.getMapper(AuthorUserMapper.class);

    AuthorUser toAuthorUser(AuthorUser AuthorUser);
}