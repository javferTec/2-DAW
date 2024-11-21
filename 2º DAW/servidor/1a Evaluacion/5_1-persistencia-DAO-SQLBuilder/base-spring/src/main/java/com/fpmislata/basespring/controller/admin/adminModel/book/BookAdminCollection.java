package com.fpmislata.basespring.controller.admin.adminModel.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookAdminCollection {
    private String isbn;
    private String title;
}

