package com.fpmislata.basespring.controller;

import com.fpmislata.basespring.data.BookTestData;
import com.fpmislata.basespring.domain.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private BookController bookController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("Test BookController implementation")
    void testBookController() {
        String url = BookController.URL;
        assertEquals("/api/books", url);
    }


    @Test
    @DisplayName("Test getAll method")
    void testGetAllMethod() throws Exception {
        // Arrange
        when(bookService.findAll(1, 10)).thenReturn(BookTestData.getBooks());

        // Act & Assert
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verificar que el código de estado sea 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].isbn").value("1111"))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[0].price").value(12.30))
                .andExpect(jsonPath("$[0].discount").value(10.0f))
                .andExpect(jsonPath("$[0].cover").value("cover1.jpg"))
                .andExpect(jsonPath("$[0].synopsis").doesNotExist())
                .andExpect(jsonPath("$[0].publisher").doesNotExist())
                .andExpect(jsonPath("$[0].category").doesNotExist())
                .andExpect(jsonPath("$[0].authors").doesNotExist())
                .andExpect(jsonPath("$[0].genres").doesNotExist());
    }


    @Test
    @DisplayName("Test findByIsn method")
    void testFindByIsnMethod() throws Exception {
        // Arrange
        when(bookService.findByIsbn("1111")).thenReturn(BookTestData.getBooks().get(0));

        // Act & Assert
        mockMvc.perform(get("/api/books/1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Verificar que el código de estado sea 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isbn").value("1111"))
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.price").value(12.30))
                .andExpect(jsonPath("$.discount").value(10.0f))
                .andExpect(jsonPath("$.cover").value("cover1.jpg"))
                .andExpect(jsonPath("$.synopsis").exists())
                .andExpect(jsonPath("$.publisher").exists())
                .andExpect(jsonPath("$.category").exists())
                .andExpect(jsonPath("$.authors").exists())
                .andExpect(jsonPath("$.authors").isArray())
                .andExpect(jsonPath("$.authors").isNotEmpty())
                .andExpect(jsonPath("$.authors.length()").value(2))
                .andExpect(jsonPath("$.authors[0].name").value("Author 1"))
                .andExpect(jsonPath("$.genres").exists());
    }


    @Test
    @DisplayName("Test findByIsbn method not found")
    void testFindByIsbnMethodNotFound() throws Exception {
        // Arrange
        String isbn = "9999";
        when(bookService.findByIsbn(isbn)).thenThrow(new ResourceNotFoundException("Book with ISBN " + isbn + " not found"));

        // Act & Assert
        mockMvc.perform(get("/api/books/{isbn}", isbn)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())  // Verificar que el código de estado sea 404
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("ResourceNotFoundException"))
                .andExpect(jsonPath("$.message").value("Resource not found. Book with ISBN " + isbn + " not found"));
    }

}
