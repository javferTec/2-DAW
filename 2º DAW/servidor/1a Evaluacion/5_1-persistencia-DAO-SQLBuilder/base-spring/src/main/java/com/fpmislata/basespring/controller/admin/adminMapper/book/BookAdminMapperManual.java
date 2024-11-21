package com.fpmislata.basespring.controller.admin.adminMapper.book;

import com.fpmislata.basespring.common.annotation.common.Mapper;
import com.fpmislata.basespring.controller.admin.adminModel.book.BookAdminCollection;
import com.fpmislata.basespring.domain.model.Book;
import org.springframework.stereotype.Component;

@Mapper
public class BookAdminMapperManual {
    public BookAdminCollection toBookAdminCollection(Book book) {
        return new BookAdminCollection(
                book.getIsbn(),
                book.getTitle()
        );
    }
}
