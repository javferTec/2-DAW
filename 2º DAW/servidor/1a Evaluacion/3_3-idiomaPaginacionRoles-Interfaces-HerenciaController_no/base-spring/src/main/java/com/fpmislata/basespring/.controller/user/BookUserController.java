package com.fpmislata.basespring.controller.user;

import com.fpmislata.basespring.controller.common.generic.BaseEntityController;
import com.fpmislata.basespring.controller.user.userMapper.book.BookUserMapper;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserCollection;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserDetail;
import com.fpmislata.basespring.domain.user.model.BookUser;
import com.fpmislata.basespring.domain.user.service.BookUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookUserController.URL)
public class BookUserController extends BaseEntityController<BookUserCollection, BookUserDetail, BookUser> {

    public static final String URL = "/api/books";
    private final BookUserService bookUserService;


    @Override
    public String getUrlController() {
        return URL;
    }

    @Override
    public List<BookUser> getAll(int page, int pageSize) {
        return bookUserService.findAll(page, pageSize);
    }

    @Override
    public int count() {
        return bookUserService.count();
    }

    @Override
    public BookUserCollection toCollection(BookUser book) {
        return BookUserMapper.INSTANCE.toBookCollection(book);
    }

    @Override
    public BookUserDetail toDetail(String isbn) {
        return BookUserMapper.INSTANCE.toBookDetail(bookUserService.findByIsbn(isbn));
    }
}

