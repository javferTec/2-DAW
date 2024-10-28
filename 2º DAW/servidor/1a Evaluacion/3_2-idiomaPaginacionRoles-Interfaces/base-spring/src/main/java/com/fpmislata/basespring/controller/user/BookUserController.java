package com.fpmislata.basespring.controller.user;

import com.fpmislata.basespring.controller.admin.adminModel.book.BookAdminCollection;
import com.fpmislata.basespring.controller.common.base.BaseBookController;
import com.fpmislata.basespring.controller.common.base.Mapper;
import com.fpmislata.basespring.controller.user.userMapper.book.BookUserMapper;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserCollection;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserDetail;
import com.fpmislata.basespring.domain.admin.model.BookAdmin;
import com.fpmislata.basespring.domain.common.base.BaseBookService;
import com.fpmislata.basespring.domain.user.model.BookUser;
import com.fpmislata.basespring.domain.user.service.BookUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookUserController.URL)
public class BookUserController extends BaseBookController<BookUser, BookUserCollection, BookUserDetail> {

    public static final String URL = "/api/books";
    private final BookUserService bookUserService;
    private final BookUserMapper bookUserMapper;

    @Override
    protected BaseBookService<BookUser> getService() {
        return bookUserService;
    }

    @Override
    protected Mapper<BookUser, BookUserCollection, BookUserDetail> getMapper() {
        return (Mapper<BookUser, BookUserCollection, BookUserDetail>) bookUserMapper;
    }

    @Override
    protected Mapper<BookAdmin, BookAdminCollection> getMapper() {
        return bookUserMapper;
    }

    @Override
    protected String getUrl() {
        return URL;
    }
}