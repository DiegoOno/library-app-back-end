package com.example.library.library_app.web.controllers;

import com.example.library.library_app.application.BookService;
import com.example.library.library_app.application.LibraryUserService;
import com.example.library.library_app.application.LoanService;

import com.example.library.library_app.web.dto.BookDTO;
import com.example.library.library_app.web.dto.LibraryUserDTO;
import com.example.library.library_app.web.dto.LoanDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public LoanDTO create(@RequestBody LoanDTO loanDTO) {
        BookDTO bookDTO = BookDTO.fromEntity(bookService.findById(loanDTO.getBookId()).orElseThrow());
        LibraryUserDTO libraryUserDTO = LibraryUserDTO.fromEntity(libraryUserService.findById(loanDTO.getLibraryUserId()).orElseThrow());

        return LoanDTO.fromEntity(loanService.create(LoanDTO.toEntity(loanDTO, bookDTO, libraryUserDTO)));
    }

    @PutMapping
    public LoanDTO update(@RequestBody LoanDTO loanDTO) {
        BookDTO bookDTO = BookDTO.fromEntity(bookService.findById(loanDTO.getBookId()).orElseThrow());
        LibraryUserDTO libraryUserDTO = LibraryUserDTO.fromEntity(libraryUserService.findById(loanDTO.getLibraryUserId()).orElseThrow());

        return LoanDTO.fromEntity(loanService.update(LoanDTO.toEntity(loanDTO, bookDTO, libraryUserDTO)));
    }
}
