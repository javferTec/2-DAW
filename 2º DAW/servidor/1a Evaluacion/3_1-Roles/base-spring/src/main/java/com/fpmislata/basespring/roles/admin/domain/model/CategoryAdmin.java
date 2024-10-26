package com.fpmislata.basespring.roles.admin.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAdmin {

    private long id;
    private String nameEs;
    private String nameEn;
    private String slug;
}