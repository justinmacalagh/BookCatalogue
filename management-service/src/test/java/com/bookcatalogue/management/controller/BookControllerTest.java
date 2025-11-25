package com.bookcatalogue.management.controller;

import com.bookcatalogue.management.model.Book;
import com.bookcatalogue.management.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;
    private Date publishDate;

    @BeforeEach
    void setUp() {
        publishDate = new Date();
        book = new Book("Test Book", "1234567890", publishDate, new BigDecimal("299.99"), "Hard Cover");
        book.setId(1L);
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        // Arrange
        List<Book> expectedBooks = Arrays.asList(book);
        when(bookService.getAllBooks()).thenReturn(expectedBooks);

        // Act
        List<Book> result = bookController.getAllBooks();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getName());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() {
        // Arrange
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        // Act
        ResponseEntity<Book> response = bookController.getBookById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Book", response.getBody().getName());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookById_WhenBookNotExists_ShouldReturnNotFound() {
        // Arrange
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Book> response = bookController.getBookById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void createBook_WithValidData_ShouldReturnCreatedBook() {
        // Arrange
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        // Act
        Book result = bookController.createBook(book);

        // Assert
        assertNotNull(result);
        assertEquals("Test Book", result.getName());
        verify(bookService, times(1)).createBook(book);
    }

    @Test
    void updateBook_WhenBookExists_ShouldReturnUpdatedBook() {
        // Arrange
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book);

        // Act
        ResponseEntity<Book> response = bookController.updateBook(1L, book);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(bookService, times(1)).updateBook(1L, book);
    }

    @Test
    void updateBook_WhenBookNotExists_ShouldReturnNotFound() {
        // Arrange
        when(bookService.updateBook(eq(1L), any(Book.class)))
                .thenThrow(new RuntimeException("Book not found"));

        // Act
        ResponseEntity<Book> response = bookController.updateBook(1L, book);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookService, times(1)).updateBook(1L, book);
    }

    @Test
    void deleteBook_WhenBookExists_ShouldReturnOk() {
        // Arrange
        doNothing().when(bookService).deleteBook(1L);

        // Act
        ResponseEntity<?> response = bookController.deleteBook(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void deleteBook_WhenBookNotExists_ShouldReturnNotFound() {
        // Arrange
        doThrow(new RuntimeException("Book not found")).when(bookService).deleteBook(1L);

        // Act
        ResponseEntity<?> response = bookController.deleteBook(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(1L);
    }
}