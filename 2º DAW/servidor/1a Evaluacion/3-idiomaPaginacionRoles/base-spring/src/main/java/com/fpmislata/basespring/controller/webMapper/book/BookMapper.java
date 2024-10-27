package com.fpmislata.basespring.controller.webMapper.book;

import com.fpmislata.basespring.controller.webMapper.author.AuthorMapper;
import com.fpmislata.basespring.controller.webMapper.genre.GenreMapper;
import com.fpmislata.basespring.controller.webMapper.publisher.PublisherMapper;
import com.fpmislata.basespring.controller.webModel.book.BookCollection;
import com.fpmislata.basespring.controller.webModel.book.BookDetail;
import com.fpmislata.basespring.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PublisherMapper.class, AuthorMapper.class, GenreMapper.class})
// @Mapper -> Indica que esta interfaz es un mapeador
// uses -> Indica otros mapeadores que se van a utilizar
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class); // Obtiene una instancia del mapeador BookMapper (Singleton) para usarlo en esta clase

    BookCollection toBookCollection(Book book); // Mapea un objeto Book a un objeto BookCollection

    @Mapping(target ="publisherCollection", source = "publisher") // Mapea el atributo publisher de Book a publisherCollection de BookDetail
    @Mapping(target="authorsCollection", source = "authors") // El primer elemento es el atributo de BookDetail y el segundo es el atributo de Book
    @Mapping(target = "category", source = "category.name")
    @Mapping(target = "genres", source = "genres")
    BookDetail toBookDetail(Book book);

}
