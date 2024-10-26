package com.fpmislata.basespring.roles.admin.controller.mapper.book;


import com.fpmislata.basespring.roles.admin.controller.model.book.BookAdminCollection;
import com.fpmislata.basespring.roles.admin.domain.model.BookAdmin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookAdminMapper {

    BookAdminMapper INSTANCE = Mappers.getMapper(BookAdminMapper.class);

    @Mapping(target = "title", source = "title")
    BookAdminCollection toBookCollection(BookAdmin bookAdmin);

}