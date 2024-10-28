package com.fpmislata.basespring.domain.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorAdmin {
    private long id;
    private String name;
    private String nationality;
    private String biographyEs;
    private String biographyEn;
    private int birthYear;
    private int deathYear;
}