package com.example.library.library_app.web.dto;

import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.domain.entity.LibraryUser;
import com.example.library.library_app.domain.entity.Loan;
import com.example.library.library_app.domain.enums.LoanStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public class LoanDTO {
    private Long id;

    @NotNull(message = "Book is required")
    private Book book;

    @NotNull(message = "User is required")
    private LibraryUser libraryUser;

    @NotNull(message = "Loan date is required")
    @PastOrPresent(message = "Loan date cannot be in the future")
    private LocalDateTime loanDate;

    @NotNull(message = "Return date is required")
    private LocalDateTime returnDate;

    @NotNull(message = "Status is required")
    private LoanStatus status;

    public LoanDTO() {
    }

    public LoanDTO(Long id, Book book, LibraryUser libraryUser, LocalDateTime loanDate, LocalDateTime returnDate, LoanStatus status) {
        this.id = id;
        this.book = book;
        this.libraryUser = libraryUser;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public static LoanDTO fromEntity(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getBook(),
                loan.getUser(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }

    public static Loan toEntity(LoanDTO loanDTO) {
        return new Loan(
                loanDTO.getId(),
                loanDTO.getLibraryUser(),
                loanDTO.getBook(),
                loanDTO.getLoanDate(),
                loanDTO.getReturnDate(),
                LoanStatus.valueOf(String.valueOf(loanDTO.getStatus()))
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LibraryUser getLibraryUser() {
        return libraryUser;
    }

    public void setLibraryUser(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }

    public @NotNull(message = "Loan date is required") @PastOrPresent(message = "Loan date cannot be in the future") LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(@NotNull(message = "Loan date is required") @PastOrPresent(message = "Loan date cannot be in the future") LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public @NotNull(message = "Return date is required") LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(@NotNull(message = "Return date is required") LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public @NotNull(message = "Status is required") LoanStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Status is required") LoanStatus status) {
        this.status = status;
    }
}
