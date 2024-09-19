package com.fpmislata.basespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @GetMapping
    public void index() {
        System.out.println("Index option");
    }

    @GetMapping("/books")
    public void books() {
        System.out.println("Books option");
    }

    @GetMapping("/books/{id}")
    public void book(@PathVariable String id) {
        System.out.println("Book option" + id);
    }

}
