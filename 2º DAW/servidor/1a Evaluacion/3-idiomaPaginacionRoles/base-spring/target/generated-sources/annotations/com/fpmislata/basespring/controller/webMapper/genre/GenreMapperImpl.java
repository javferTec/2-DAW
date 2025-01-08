package com.fpmislata.basespring.controller.webMapper.genre;

import com.fpmislata.basespring.domain.model.Genre;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-23T10:37:22+0200",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
public class GenreMapperImpl implements GenreMapper {

    @Override
    public List<String> toGenreNameList(List<Genre> genres) {
        if ( genres == null ) {
            return null;
        }

        List<String> list = new ArrayList<String>( genres.size() );
        for ( Genre genre : genres ) {
            list.add( toGenreName( genre ) );
        }

        return list;
    }
}
