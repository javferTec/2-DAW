package com.fpmislata.basespring.controller.common.userAdminMapper.genre;

import com.fpmislata.basespring.domain.user.model.GenreUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    List<String> toGenreNameList(List<GenreUser> genreUsers);

    default String toGenreName(GenreUser genreUser) { // Metodo para mapear un objeto Genre a un String que MapStruct usara por defecto.
        return genreUser.getName();
    }
}