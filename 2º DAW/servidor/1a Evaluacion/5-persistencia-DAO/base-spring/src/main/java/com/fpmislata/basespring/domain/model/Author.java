package com.fpmislata.basespring.domain.model;

import com.fpmislata.basespring.common.annotation.persistence.Column;
import com.fpmislata.basespring.common.annotation.persistence.PrimaryKey;
import com.fpmislata.basespring.common.annotation.persistence.Table;
import com.fpmislata.basespring.common.locale.LanguageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authors")
public class Author {

    @PrimaryKey
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "biography_es")
    private String biographyEs;

    @Column(name = "biography_en")
    private String biographyEn;

    @Column(name = "birth_year")
    private int birthYear;

    @Column(name = "death_year")
    private int deathYear;

    public String getBiography() {
        String language = LanguageUtils.getCurrentLanguage();
        if ("en".equals(language)) {
            return biographyEn;
        }
        return biographyEs;
    }
}