package com.fpmislata.basespring.domain.model;

import com.fpmislata.basespring.common.annotation.persistence.*;
import com.fpmislata.basespring.common.locale.LanguageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @PrimaryKey
    @Column(name = "id")
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title_es")
    private String titleEs;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "synopsis_es")
    private String synopsisEs;

    @Column(name = "synopsis_en")
    private String synopsisEn;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "discount")
    private float discount;

    @Column(name = "cover")
    private String cover;

    @OneToOne(targetEntity = Publisher.class)
    private Publisher publisher;

    @OneToOne(targetEntity = Category.class)
    private Category category;

    @ManyToMany(targetEntity = Genre.class,
            joinTable = "books_genres",
            joinColumn = "book_id",
            inverseJoinColumn = "genre_id")
    private List<Genre> genres;

    @ManyToMany(targetEntity = Author.class,
            joinTable = "books_authors",
            joinColumn = "book_id",
            inverseJoinColumn = "author_id")
    private List<Author> authors;

    public String getTitle() {
        String language = LanguageUtils.getCurrentLanguage();
        if ("en".equals(language)) {
            return titleEn;
        }
        return titleEs;
    }

    public String getSynopsis() {
        String language = LanguageUtils.getCurrentLanguage();
        if ("en".equals(language)) {
            return synopsisEn;
        }
        return synopsisEs;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }
}