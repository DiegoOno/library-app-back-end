package com.example.library.library_app.domain.repository;

import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.domain.entity.Loan;
import com.example.library.library_app.domain.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long>{
    Optional<Loan> findByBookAndStatus(Book book, LoanStatus status);

    @Query(value = "SELECT DISTINCT b.category FROM loan l JOIN book b ON l.book_id = b.id WHERE l.library_user_id = :userId", nativeQuery = true)
    List<String> findDistinctCategoriesByUserId(@Param("userId") Long userId);
}
