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
@Table(name = "categories")
public class Category {

    @PrimaryKey
    @Column(name = "id")
    private Long id;

    @Column(name = "name_es")
    private String nameEs;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "slug")
    private String slug;

    public String getName() {
        String language = LanguageUtils.getCurrentLanguage();
        if ("en".equals(language)) {
            return nameEn;
        }
        return nameEs;
    }
}
