package com.fpmislata.basespring.controller.webMapper.publisher;

import com.fpmislata.basespring.controller.webModel.publisher.PublisherCollection;
import com.fpmislata.basespring.domain.model.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherCollection toPublisherCollection(Publisher publisher);
}
