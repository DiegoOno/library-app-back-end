package com.example.library.library_app.domain.entity;

import com.example.library.library_app.domain.enums.LoanStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "loan")
public class Loan {
    public Loan() {
    }

    public Loan(LibraryUser libraryUser, Book book, LocalDateTime loanDate, LocalDateTime returnDate, LoanStatus status) {
        this.libraryUser = libraryUser;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public Loan(Long id, LibraryUser libraryUser, Book book, LocalDateTime loanDate, LocalDateTime returnDate, LoanStatus status) {
        this.id = id;
        this.libraryUser = libraryUser;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "library_user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private LibraryUser libraryUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Book book;

    @Column(name = "loan_date", nullable = false)
    private LocalDateTime loanDate;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status;

    public void mergeForUpdate(Loan loan) {
        this.status = loan.getStatus();
        this.returnDate = loan.getReturnDate();
        this.loanDate = loan.getLoanDate();
        this.book = loan.getBook();
        this.libraryUser = loan.getLibraryUser();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LibraryUser getUser() {
        return libraryUser;
    }

    public void setUser(LibraryUser user) {
        this.libraryUser = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public LibraryUser getLibraryUser() {
        return libraryUser;
    }

    public void setLibraryUser(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }
}
