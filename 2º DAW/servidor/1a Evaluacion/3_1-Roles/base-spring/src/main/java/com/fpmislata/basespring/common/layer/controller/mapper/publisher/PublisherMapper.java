package com.fpmislata.basespring.common.layer.controller.mapper.publisher;


import com.fpmislata.basespring.common.layer.controller.model.publisher.PublisherCollection;
import com.fpmislata.basespring.roles.user.domain.model.PublisherUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherCollection toPublisherCollection(PublisherUser publisher);
}
