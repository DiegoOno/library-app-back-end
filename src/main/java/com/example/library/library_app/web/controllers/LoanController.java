package com.example.library.library_app.web.controllers;

import com.example.library.library_app.application.BookService;
import com.example.library.library_app.application.LibraryUserService;
import com.example.library.library_app.application.LoanService;

import com.example.library.library_app.domain.enums.LoanStatus;
import com.example.library.library_app.web.dto.BookDTO;
import com.example.library.library_app.web.dto.LibraryUserDTO;
import com.example.library.library_app.web.dto.LoanDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;
    private final LibraryUserService libraryUserService;
    private final BookService bookService;

    public LoanController(LoanService loanService, LibraryUserService libraryUserService, BookService bookService) {
        this.loanService = loanService;
        this.libraryUserService = libraryUserService;
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LoanDTO loanDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        BookDTO bookDTO = BookDTO.fromEntity(bookService.findById(loanDTO.getBookId()).orElseThrow());
        LibraryUserDTO libraryUserDTO = LibraryUserDTO.fromEntity(libraryUserService.findById(loanDTO.getLibraryUserId()).orElseThrow());

        if (loanService.findByBookAndStatus(BookDTO.toEntity(bookDTO), LoanStatus.ACTIVE).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is already loaned");
        }

        var createdLoanDTO = LoanDTO.fromEntity(loanService.create(LoanDTO.toEntity(loanDTO, bookDTO, libraryUserDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoanDTO);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody LoanDTO loanDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        BookDTO bookDTO = BookDTO.fromEntity(bookService.findById(loanDTO.getBookId()).orElseThrow());
        LibraryUserDTO libraryUserDTO = LibraryUserDTO.fromEntity(libraryUserService.findById(loanDTO.getLibraryUserId()).orElseThrow());

        var updatedLoanDTO = LoanDTO.fromEntity(loanService.update(LoanDTO.toEntity(loanDTO, bookDTO, libraryUserDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedLoanDTO);
    }
}
