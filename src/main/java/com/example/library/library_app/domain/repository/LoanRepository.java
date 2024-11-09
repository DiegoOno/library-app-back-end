package com.example.library.library_app.domain.repository;

import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.domain.entity.Loan;
import com.example.library.library_app.domain.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long>{
    Optional<Loan> findByBookAndStatus(Book book, LoanStatus status);
}