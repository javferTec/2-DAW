package com.fpmislata.basespring.roles.user.controller.webMapper.publisher;

import com.fpmislata.basespring.roles.user.controller.webModel.publisher.PublisherCollection;
import com.fpmislata.basespring.roles.user.domain.model.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherCollection toPublisherCollection(Publisher publisher);
}
