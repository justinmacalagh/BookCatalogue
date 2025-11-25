package com.bookcatalogue.management.repo;

import com.bookcatalogue.management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbnNumber(String isbnNumber);
    Book findByIsbnNumber(String isbnNumber);
}