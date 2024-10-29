package com.fpmislata.basespring.controller.admin;

import com.fpmislata.basespring.controller.admin.adminMapper.book.BookAdminMapper;
import com.fpmislata.basespring.controller.admin.adminModel.book.BookAdminCollection;
import com.fpmislata.basespring.controller.common.generic.BaseEntityController;
import com.fpmislata.basespring.domain.roles.admin.model.BookAdmin;
import com.fpmislata.basespring.domain.roles.admin.service.BookAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookAdminController.URL)
public class BookAdminController extends BaseEntityController<BookAdminCollection, BookAdmin, BookAdmin> {

    public static final String URL = "/api/admin/books";
    private final BookAdminService bookAdminService;

    @Override
    public String getUrlController() {
        return URL;
    }


    @Override
    public List<BookAdmin> getAll(int page, int pageSize) {
        return bookAdminService.getAll(page, pageSize);
    }

    @Override
    public int count() {
        return bookAdminService.count();
    }

    @Override
    public BookAdminCollection toCollection(BookAdmin book) {
        return BookAdminMapper.INSTANCE.toBookCollection(book);
    }

    @Override
    public BookAdmin toDetail(String isbn) {
        return bookAdminService.findByIsbn(isbn);
    }


}
