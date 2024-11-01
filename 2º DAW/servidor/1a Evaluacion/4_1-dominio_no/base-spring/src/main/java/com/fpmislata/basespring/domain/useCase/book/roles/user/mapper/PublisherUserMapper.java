package com.fpmislata.basespring.domain.useCase.book.roles.user.mapper;

import com.fpmislata.basespring.domain.useCase.book.roles.user.model.PublisherUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherUserMapper {

    PublisherUserMapper INSTANCE = Mappers.getMapper(PublisherUserMapper.class);

    PublisherUser toPublihserUser(PublisherUser publisherUser);

}