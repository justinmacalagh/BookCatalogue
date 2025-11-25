package com.bookcatalogue.web.controller;

import com.bookcatalogue.web.client.ManagementServiceClient;
import com.bookcatalogue.web.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
   Book Controller to handle incoming requests
 */

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private ManagementServiceClient managementServiceClient;

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = managementServiceClient.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("book", new Book());
        return "books";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        managementServiceClient.createBook(book);
        return "redirect:/books";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
        managementServiceClient.updateBook(id, book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        managementServiceClient.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = managementServiceClient.getBookById(id);
        List<Book> books = managementServiceClient.getAllBooks();

        model.addAttribute("books", books);
        model.addAttribute("book", book);
        model.addAttribute("editMode", true);

        return "books";
    }
}