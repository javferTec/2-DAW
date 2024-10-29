package com.fpmislata.basespring.domain.roles.admin.model;

import com.fpmislata.basespring.common.locale.LanguageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAdmin {

    private long id;
    private String isbn;
    private String titleEs;
    private String titleEn;
    private String synopsisEs;
    private String synopsisEn;
    private BigDecimal price;
    private float discount;
    private String cover;
    private PublisherAdmin publisherAdmin;
    private CategoryAdmin categoryAdmin;
    private List<GenreAdmin> genreAdmins;
    private List<AuthorAdmin> authorAdmins;

    public String getTitle() {
        String language = LanguageUtils.getCurrentLanguage();
        if ("en".equals(language)) {
            return titleEn;
        }
        return titleEs;
    }
}