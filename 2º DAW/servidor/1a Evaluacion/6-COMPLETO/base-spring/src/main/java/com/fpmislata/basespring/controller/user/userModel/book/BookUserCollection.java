package com.fpmislata.basespring.controller.user.userModel.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUserCollection {
    private String isbn;
    private String title;
    private BigDecimal price;
    private float discount;
    private String cover;
}
