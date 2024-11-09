package com.example.library.library_app.web.controllers;

import com.example.library.library_app.application.BookService;
import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.web.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> findAll() {
        return bookService.findAll().stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO findById(@PathVariable("id") Long id) {
        return bookService.findById(id)
                .map(BookDTO::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Book not found with id " + id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        Book book = bookService.create(BookDTO.toEntity(bookDTO));
        return BookDTO.fromEntity(book);
    }

    @PutMapping
    public BookDTO update(@RequestBody BookDTO bookDTO) {
        Book book = bookService.update(BookDTO.toEntity(bookDTO));
        return BookDTO.fromEntity(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookService.delete(id);
    }
}
