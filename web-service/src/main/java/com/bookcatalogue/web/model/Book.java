package com.bookcatalogue.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  Model for a book
 **/

public class Book {
    private Long id;
    private String name;
    private String isbnNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    private BigDecimal price;
    private String bookType;

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