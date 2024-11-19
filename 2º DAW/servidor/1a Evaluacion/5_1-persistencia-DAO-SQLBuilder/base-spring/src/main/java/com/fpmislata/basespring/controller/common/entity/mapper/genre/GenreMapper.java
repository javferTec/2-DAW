package com.fpmislata.basespring.controller.common.entity.mapper.genre;

import com.fpmislata.basespring.domain.model.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    List<String> toGenreNameList(List<Genre> genreUsers);

    default String toGenreName(Genre genreUser) {
        return genreUser.getName();
    }
}