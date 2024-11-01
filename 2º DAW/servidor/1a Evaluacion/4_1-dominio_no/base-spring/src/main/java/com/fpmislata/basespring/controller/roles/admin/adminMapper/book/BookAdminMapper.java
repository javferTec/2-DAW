package com.fpmislata.basespring.controller.roles.admin.adminMapper.book;

import com.fpmislata.basespring.controller.roles.admin.adminModel.book.BookAdminCollection;
import com.fpmislata.basespring.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookAdminMapper {

    BookAdminMapper INSTANCE = Mappers.getMapper(BookAdminMapper.class);

    @Mapping(target = "title", source = "title")
    BookAdminCollection toBookCollection(Book book);
}