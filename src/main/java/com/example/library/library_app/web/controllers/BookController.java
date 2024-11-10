package com.example.library.library_app.web.controllers;

import com.example.library.library_app.application.BookService;
import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.web.dto.BookDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

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

    @GetMapping("/available")
    public List<BookDTO> findBooksAvailableForLoan() {
        return bookService.findBooksAvailableForLoan().stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable("id") Long id) {
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok(BookDTO.fromEntity(book)))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Book not found with id " + id
                ));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BookDTO bookDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        Book book = bookService.create(BookDTO.toEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(BookDTO.fromEntity(book));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody BookDTO bookDTO, BindingResult result) {
        if (isNull(bookDTO.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        Book book = bookService.update(BookDTO.toEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(BookDTO.fromEntity(book));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookService.delete(id);
    }
}
