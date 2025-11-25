package com.bookcatalogue.management.repo;

import com.bookcatalogue.management.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void saveBook_ShouldPersistBook() {
        // Arrange
        Date publishDate = new Date();
        Book book = new Book("Repository Test Book", "9876543210", publishDate, new BigDecimal("199.99"), "Soft Cover");

        // Act
        Book savedBook = bookRepository.save(book);

        // Assert
        assertNotNull(savedBook.getId());
        assertEquals("Repository Test Book", savedBook.getName());
        assertEquals("9876543210", savedBook.getIsbnNumber());
        assertEquals(new BigDecimal("199.99"), savedBook.getPrice());
        assertEquals("Soft Cover", savedBook.getBookType());
    }

    @Test
    void findByIsbnNumber_WhenBookExists_ShouldReturnBook() {
        // Arrange
        Date publishDate = new Date();
        Book book = new Book("ISBN Search Book", "5555555555", publishDate, new BigDecimal("150.00"), "eBook");
        bookRepository.save(book);

        // Act
        Book foundBook = bookRepository.findByIsbnNumber("5555555555");

        // Assert
        assertNotNull(foundBook);
        assertEquals("ISBN Search Book", foundBook.getName());
    }

    @Test
    void existsByIsbnNumber_WhenBookExists_ShouldReturnTrue() {
        // Arrange
        Date publishDate = new Date();
        Book book = new Book("Exists Check Book", "6666666666", publishDate, new BigDecimal("250.00"), "Hard Cover");
        bookRepository.save(book);

        // Act
        boolean exists = bookRepository.existsByIsbnNumber("6666666666");

        // Assert
        assertTrue(exists);
    }

    @Test
    void existsByIsbnNumber_WhenBookNotExists_ShouldReturnFalse() {
        // Act
        boolean exists = bookRepository.existsByIsbnNumber("9999999999");

        // Assert
        assertFalse(exists);
    }

    @Test
    void findAll_ShouldReturnAllBooks() {
        // Arrange
        Date publishDate = new Date();
        Book book1 = new Book("Book One", "1111111111", publishDate, new BigDecimal("100.00"), "Hard Cover");
        Book book2 = new Book("Book Two", "2222222222", publishDate, new BigDecimal("200.00"), "Soft Cover");
        bookRepository.save(book1);
        bookRepository.save(book2);

        // Act
        List<Book> books = bookRepository.findAll();

        // Assert
        assertEquals(2, books.size());
    }

    @Test
    void findById_WhenBookExists_ShouldReturnBook() {
        // Arrange
        Date publishDate = new Date();
        Book book = new Book("Find By ID Book", "3333333333", publishDate, new BigDecimal("300.00"), "eBook");
        Book savedBook = bookRepository.save(book);

        // Act
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        // Assert
        assertTrue(foundBook.isPresent());
        assertEquals("Find By ID Book", foundBook.get().getName());
    }
}