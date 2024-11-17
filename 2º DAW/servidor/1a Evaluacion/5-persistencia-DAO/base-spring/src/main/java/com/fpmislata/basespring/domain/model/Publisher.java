package com.fpmislata.basespring.domain.model;

import com.fpmislata.basespring.common.annotation.persistence.Column;
import com.fpmislata.basespring.common.annotation.persistence.PrimaryKey;
import com.fpmislata.basespring.common.annotation.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "publishers")
public class Publisher {

    @PrimaryKey
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;
}