package com.fpmislata.basespring.domain.useCase.book.roles.user.mapper;

import com.fpmislata.basespring.domain.useCase.book.roles.user.model.GenreUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenreUserMapper {

    GenreUserMapper INSTANCE = Mappers.getMapper(GenreUserMapper.class);

    GenreUser toGenreUser(GenreUser genreUser);

}