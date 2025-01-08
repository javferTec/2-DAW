package com.fpmislata.basespring.controller.webMapper.publisher;

import com.fpmislata.basespring.controller.webModel.publisher.PublisherCollection;
import com.fpmislata.basespring.domain.model.Publisher;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-23T10:37:23+0200",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
public class PublisherMapperImpl implements PublisherMapper {

    @Override
    public PublisherCollection toPublisherCollection(Publisher publisher) {
        if ( publisher == null ) {
            return null;
        }

        long id = 0L;
        String name = null;

        id = publisher.getId();
        name = publisher.getName();

        PublisherCollection publisherCollection = new PublisherCollection( id, name );

        return publisherCollection;
    }
}
