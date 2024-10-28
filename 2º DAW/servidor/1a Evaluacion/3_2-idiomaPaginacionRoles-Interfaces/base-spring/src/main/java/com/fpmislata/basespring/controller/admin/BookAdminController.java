package com.fpmislata.basespring.controller.admin;

import com.fpmislata.basespring.controller.admin.adminMapper.book.BookAdminMapper;
import com.fpmislata.basespring.controller.admin.adminModel.book.BookAdminCollection;
import com.fpmislata.basespring.controller.common.base.Mapper;
import com.fpmislata.basespring.domain.admin.model.BookAdmin;
import com.fpmislata.basespring.domain.admin.service.BookAdminService;
import com.fpmislata.basespring.domain.common.base.BaseBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookAdminController.URL)
public class BookAdminController extends BaseBookController<BookAdmin, BookAdminCollection, BookAdminDetail> {

    public static final String URL = "/api/admin/books";
    private final BookAdminService bookAdminService;
    private final BookAdminMapper bookAdminMapper;

    @Override
    protected BaseBookService<BookAdmin> getService() {
        return (BaseBookService<BookAdmin>) bookAdminService;
    }

    @Override
    protected Mapper<BookAdmin, BookAdminCollection> getMapper() {
        return bookAdminMapper;
    }

    protected String getUrl() {
        return URL;
    }
}