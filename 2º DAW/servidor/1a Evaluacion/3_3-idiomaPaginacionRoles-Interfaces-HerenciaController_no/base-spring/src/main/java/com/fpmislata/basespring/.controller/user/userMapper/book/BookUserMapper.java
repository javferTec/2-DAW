package com.fpmislata.basespring.controller.user.userMapper.book;

import com.fpmislata.basespring.controller.common.userAdminMapper.author.AuthorMapper;
import com.fpmislata.basespring.controller.common.userAdminMapper.genre.GenreMapper;
import com.fpmislata.basespring.controller.common.userAdminMapper.publisher.PublisherMapper;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserCollection;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserDetail;
import com.fpmislata.basespring.domain.user.model.BookUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PublisherMapper.class, AuthorMapper.class, GenreMapper.class})
// @Mapper -> Indica que esta interfaz es un mapeador
// uses -> Indica otros mapeadores que se van a utilizar
public interface BookUserMapper {

    BookUserMapper INSTANCE = Mappers.getMapper(BookUserMapper.class); // Obtiene una instancia del mapeador BookMapper (Singleton) para usarlo en esta clase

    BookUserCollection toBookCollection(BookUser bookUser); // Mapea un objeto Book a un objeto BookCollection

    @Mapping(target ="publisherCollection", source = "publisherUser") // Mapea el atributo publisher de Book a publisherCollection de BookDetail
    @Mapping(target="authorsCollection", source = "authorUsers") // El primer elemento es el atributo de BookDetail y el segundo es el atributo de Book
    @Mapping(target = "category", source = "categoryUser.name")
    @Mapping(target = "genres", source = "genreUsers")
    BookUserDetail toBookDetail(BookUser bookUser);

}
