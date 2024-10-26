package com.fpmislata.basespring.roles.user.controller.model.book;

import java.math.BigDecimal;

public record BookUserCollection(
        String isbn,
        String title,
        BigDecimal price,
        float discount,
        String cover
) {
}
