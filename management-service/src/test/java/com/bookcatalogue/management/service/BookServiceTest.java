package com.bookcatalogue.management.service;

import com.bookcatalogue.management.model.Book;
import com.bookcatalogue.management.repo.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Date publishDate;

    @BeforeEach
    void setUp() {
        publishDate = new Date();
        book = new Book("Test Book", "1234567890", publishDate, new BigDecimal("299.99"), "Hard Cover");
        book.setId(1L);
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        // Arrange
        List<Book> expectedBooks = Arrays.asList(book);
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        // Act
        List<Book> actualBooks = bookService.getAllBooks();

        // Assert
        assertNotNull(actualBooks);
        assertEquals(1, actualBooks.size());
        assertEquals("Test Book", actualBooks.get(0).getName());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Optional<Book> result = bookService.getBookById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Book", result.get().getName());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_WhenBookNotExists_ShouldReturnEmpty() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Book> result = bookService.getBookById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void createBook_WithValidData_ShouldSaveBook() {
        // Arrange
        when(bookRepository.existsByIsbnNumber("1234567890")).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        Book result = bookService.createBook(book);

        // Assert
        assertNotNull(result);
        assertEquals("Test Book", result.getName());
        verify(bookRepository, times(1)).existsByIsbnNumber("1234567890");
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void createBook_WithDuplicateISBN_ShouldThrowException() {
        // Arrange
        when(bookRepository.existsByIsbnNumber("1234567890")).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.createBook(book);
        });

        assertEquals("Book with ISBN 1234567890 already exists", exception.getMessage());
        verify(bookRepository, times(1)).existsByIsbnNumber("1234567890");
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void updateBook_WhenBookExists_ShouldUpdateAndReturnBook() {
        // Arrange
        Book updatedBook = new Book("Updated Book", "1234567890", publishDate, new BigDecimal("399.99"), "eBook");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Act
        Book result = bookService.updateBook(1L, updatedBook);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Book", result.getName());
        assertEquals(new BigDecimal("399.99"), result.getPrice());
        assertEquals("eBook", result.getBookType());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_WhenBookNotExists_ShouldThrowException() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.updateBook(1L, book);
        });

        assertEquals("Book not found with id: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_WhenBookExists_ShouldDeleteBook() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        bookService.deleteBook(1L);

        // Assert
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void deleteBook_WhenBookNotExists_ShouldThrowException() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.deleteBook(1L);
        });

        assertEquals("Book not found with id: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).delete(any(Book.class));
    }
}