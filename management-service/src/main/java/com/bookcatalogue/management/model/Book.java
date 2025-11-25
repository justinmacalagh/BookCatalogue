package com.bookcatalogue.management.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "isbn_number", nullable = false, unique = true)
    private String isbnNumber;

    @Column(name = "publish_date")
    @Temporal(TemporalType.DATE)
    private Date publishDate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "book_type", nullable = false)
    private String bookType;


    /**
     *  Model for a book
     **/

    public Book() {}

    public Book(String name, String isbnNumber, Date publishDate, BigDecimal price, String bookType) {
        this.name = name;
        this.isbnNumber = isbnNumber;
        this.publishDate = publishDate;
        this.price = price;
        this.bookType = bookType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIsbnNumber() { return isbnNumber; }
    public void setIsbnNumber(String isbnNumber) { this.isbnNumber = isbnNumber; }

    public Date getPublishDate() { return publishDate; }
    public void setPublishDate(Date publishDate) { this.publishDate = publishDate; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getBookType() { return bookType; }
    public void setBookType(String bookType) { this.bookType = bookType; }
}