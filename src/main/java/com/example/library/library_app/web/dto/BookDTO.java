package com.example.library.library_app.web.dto;

import com.example.library.library_app.domain.entity.Book;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookDTO {
    private Long id;

    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Author is required")
    private String author;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    @NotNull(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Category is required")
    private String category;

    private List<LoanDTO> loans;

    public BookDTO() {
    }

    public BookDTO(Long id, String title, String author, LocalDate releaseDate, String isbn, String category, List<LoanDTO> loans) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.isbn = isbn;
        this.category = category;
        this.loans = loans;
    }

    public static BookDTO fromEntity(Book book) {
        List<LoanDTO> loanDTOs = book.getLoans().stream()
                .map(LoanDTO::fromEntity)
                .collect(Collectors.toList());

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getReleaseDate(),
                book.getIsbn(),
                book.getCategory(),
                loanDTOs
        );
    }

    public static Book toEntity(BookDTO bookDTO) {
        return new Book(
                bookDTO.getId(),
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getReleaseDate(),
                bookDTO.getIsbn(),
                bookDTO.getCategory()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<LoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanDTO> loans) {
        this.loans = loans;
    }
}
