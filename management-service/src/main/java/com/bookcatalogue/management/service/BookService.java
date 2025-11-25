package com.bookcatalogue.management.service;

import com.bookcatalogue.management.model.Book;
import com.bookcatalogue.management.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        if (bookRepository.existsByIsbnNumber(book.getIsbnNumber())) {
            throw new RuntimeException("Book with ISBN " + book.getIsbnNumber() + " already exists");
        }
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        book.setName(bookDetails.getName());
        book.setIsbnNumber(bookDetails.getIsbnNumber());
        book.setPublishDate(bookDetails.getPublishDate());
        book.setPrice(bookDetails.getPrice());
        book.setBookType(bookDetails.getBookType());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        bookRepository.delete(book);
    }
}