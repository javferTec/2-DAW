package com.fpmislata.basespring.controller.webMapper.genre;

import com.fpmislata.basespring.domain.model.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    List<String> toGenreNameList(List<Genre> genres);

    default String toGenreName(Genre genre) { // Metodo para mapear un objeto Genre a un String que MapStruct usara por defecto.
        return genre.getName();
    }
}