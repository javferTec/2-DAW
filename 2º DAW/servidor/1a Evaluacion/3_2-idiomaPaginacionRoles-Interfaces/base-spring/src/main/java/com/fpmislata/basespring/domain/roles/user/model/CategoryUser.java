package com.fpmislata.basespring.domain.roles.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUser {
    private long id;
    private String name;
    private String slug;
}
