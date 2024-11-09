package com.example.library.library_app.domain.repository;

import com.example.library.library_app.domain.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long>{
}