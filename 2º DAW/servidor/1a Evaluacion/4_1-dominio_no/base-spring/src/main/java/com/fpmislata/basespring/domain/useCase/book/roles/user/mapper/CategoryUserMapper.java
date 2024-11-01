package com.fpmislata.basespring.domain.useCase.book.roles.user.mapper;

import com.fpmislata.basespring.domain.useCase.book.roles.user.model.CategoryUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryUserMapper {

    CategoryUserMapper INSTANCE = Mappers.getMapper(CategoryUserMapper.class);

    CategoryUser toCategoryUser(CategoryUser categoryUser);

}