package com.fpmislata.basespring.controller.common.userAdminMapper.publisher;

import com.fpmislata.basespring.controller.common.userAdminModel.publisher.PublisherCollection;
import com.fpmislata.basespring.domain.user.model.PublisherUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherCollection toPublisherCollection(PublisherUser publisherUser);
}
