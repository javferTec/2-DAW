package com.fpmislata.basespring.common.layer.controller.mapper.genre;

import com.fpmislata.basespring.roles.user.domain.model.GenreUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    List<String> toGenreNameList(List<GenreUser> genreUsers);

    default String toGenreName(GenreUser genreUser) {
        return genreUser.getName();
    }
}