package com.fpmislata.basespring.controller.admin.adminMapper.book;

import com.fpmislata.basespring.controller.admin.adminModel.book.BookAdminCollection;
import com.fpmislata.basespring.domain.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookAdminMapperManual {
    public BookAdminCollection toBookAdminCollection(Book book) {
        return new BookAdminCollection(
                book.getIsbn(),
                book.getTitle()
        );
    }
}
