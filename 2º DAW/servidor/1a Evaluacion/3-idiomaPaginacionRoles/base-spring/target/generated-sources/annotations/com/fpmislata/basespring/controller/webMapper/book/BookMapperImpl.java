package com.fpmislata.basespring.controller.webMapper.book;

import com.fpmislata.basespring.controller.webMapper.author.AuthorMapper;
import com.fpmislata.basespring.controller.webMapper.genre.GenreMapper;
import com.fpmislata.basespring.controller.webMapper.publisher.PublisherMapper;
import com.fpmislata.basespring.controller.webModel.author.AuthorCollection;
import com.fpmislata.basespring.controller.webModel.book.BookCollection;
import com.fpmislata.basespring.controller.webModel.book.BookDetail;
import com.fpmislata.basespring.controller.webModel.publisher.PublisherCollection;
import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.model.Category;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-23T10:37:23+0200",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
public class BookMapperImpl implements BookMapper {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;
    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;
    private final GenreMapper genreMapper = GenreMapper.INSTANCE;

    @Override
    public BookCollection toBookCollection(Book book) {
        if ( book == null ) {
            return null;
        }

        String isbn = null;
        String title = null;
        BigDecimal price = null;
        float discount = 0.0f;
        String cover = null;

        isbn = book.getIsbn();
        title = book.getTitle();
        price = book.getPrice();
        discount = book.getDiscount();
        cover = book.getCover();

        BookCollection bookCollection = new BookCollection( isbn, title, price, discount, cover );

        return bookCollection;
    }

    @Override
    public BookDetail toBookDetail(Book book) {
        if ( book == null ) {
            return null;
        }

        PublisherCollection publisherCollection = null;
        List<AuthorCollection> authorsCollection = null;
        String category = null;
        List<String> genres = null;
        String isbn = null;
        String title = null;
        BigDecimal price = null;
        float discount = 0.0f;
        String synopsis = null;
        String cover = null;

        publisherCollection = publisherMapper.toPublisherCollection( book.getPublisher() );
        authorsCollection = authorListToAuthorCollectionList( book.getAuthors() );
        category = bookCategoryName( book );
        genres = genreMapper.toGenreNameList( book.getGenres() );
        isbn = book.getIsbn();
        title = book.getTitle();
        price = book.getPrice();
        discount = book.getDiscount();
        synopsis = book.getSynopsis();
        cover = book.getCover();

        BookDetail bookDetail = new BookDetail( isbn, title, price, discount, synopsis, cover, genres, category, publisherCollection, authorsCollection );

        return bookDetail;
    }

    protected List<AuthorCollection> authorListToAuthorCollectionList(List<Author> list) {
        if ( list == null ) {
            return null;
        }

        List<AuthorCollection> list1 = new ArrayList<AuthorCollection>( list.size() );
        for ( Author author : list ) {
            list1.add( authorMapper.toAuthorCollection( author ) );
        }

        return list1;
    }

    private String bookCategoryName(Book book) {
        Category category = book.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getName();
    }
}
