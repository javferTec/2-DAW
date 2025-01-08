package com.fpmislata.basespring.controller.webMapper.author;

import com.fpmislata.basespring.controller.webModel.author.AuthorCollection;
import com.fpmislata.basespring.domain.model.Author;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-23T10:37:23+0200",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorCollection toAuthorCollection(Author author) {
        if ( author == null ) {
            return null;
        }

        long id = 0L;
        String name = null;

        id = author.getId();
        name = author.getName();

        AuthorCollection authorCollection = new AuthorCollection( id, name );

        return authorCollection;
    }
}
