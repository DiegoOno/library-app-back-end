package com.example.library.library_app.web.dto;

import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.domain.entity.LibraryUser;
import com.example.library.library_app.domain.entity.Loan;
import com.example.library.library_app.domain.enums.LoanStatus;

import java.time.LocalDateTime;

public class LoanDTO {
    private Long id;
    private Long bookId;
    private Long libraryUserId;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private LoanStatus status;

    public LoanDTO() {}

    public LoanDTO(Long id, Long bookId, Long libraryUserId, LocalDateTime loanDate, LocalDateTime returnDate, LoanStatus status) {
        this.id = id;
        this.bookId = bookId;
        this.libraryUserId = libraryUserId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public static LoanDTO fromEntity(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getBook().getId(),
                loan.getUser().getId(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }

    public static Loan toEntity(LoanDTO loanDTO, BookDTO bookDTO, LibraryUserDTO libraryUserDTO) {
        Book book = BookDTO.toEntity(bookDTO);
        LibraryUser libraryUser = LibraryUserDTO.toEntity(libraryUserDTO);

        return new Loan(
                loanDTO.getId(),
                libraryUser,
                book,
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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getLibraryUserId() {
        return libraryUserId;
    }

    public void setLibraryUserId(Long libraryUserId) {
        this.libraryUserId = libraryUserId;
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
}
