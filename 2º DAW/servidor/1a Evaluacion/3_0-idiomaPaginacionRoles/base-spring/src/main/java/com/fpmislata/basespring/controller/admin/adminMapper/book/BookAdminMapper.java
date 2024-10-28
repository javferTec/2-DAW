package com.fpmislata.basespring.controller.admin.adminMapper.book;

import com.fpmislata.basespring.controller.admin.adminModel.book.BookAdminCollection;
import com.fpmislata.basespring.domain.admin.model.BookAdmin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookAdminMapper {

    BookAdminMapper INSTANCE = Mappers.getMapper(BookAdminMapper.class);

    @Mapping(target = "title", source = "title")
    BookAdminCollection toBookCollection(BookAdmin bookAdmin);

}