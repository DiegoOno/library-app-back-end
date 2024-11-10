package com.example.library.library_app.application;

import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.domain.entity.Loan;
import com.example.library.library_app.domain.enums.LoanStatus;
import com.example.library.library_app.domain.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Transactional(readOnly = true)
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Loan create(Loan loan) {
        return loanRepository.save(loan);
    }

    @Transactional(readOnly = true)
    public Optional<Loan> findByBookAndStatus(Book book, LoanStatus status) {
        return loanRepository.findByBookAndStatus(book, status);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Loan update(Loan loan) {
        Loan loanEntity = loanRepository.findById(loan.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));
        loanEntity.mergeForUpdate(loan);

        return loanRepository.save(loan);
    }
}
