package com.fpmislata.basespring.domain.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherAdmin {

    private long id;
    private String name;
    private String slug;
}