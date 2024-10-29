package com.fpmislata.basespring.controller.user.userModel.book;

import java.math.BigDecimal;

public record BookUserCollection(
        String isbn,
        String title,
        BigDecimal price,
        float discount,
        String cover
) {
}
