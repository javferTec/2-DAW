package com.fpmislata.basespring.controller.common.entity.mapper.publisher;

import com.fpmislata.basespring.controller.common.entity.model.publisher.PublisherCollection;
import com.fpmislata.basespring.domain.roles.user.model.PublisherUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherCollection toPublisherCollection(PublisherUser publisherUser);
}
