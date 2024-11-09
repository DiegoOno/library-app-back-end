package com.example.library.library_app.application;

import com.example.library.library_app.domain.entity.Loan;
import com.example.library.library_app.domain.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Loan update(Loan loan) {
        Loan loanEntity = loanRepository.findById(loan.getId()).orElseThrow(() -> new RuntimeException("Loan not found"));
        loanEntity.mergeForUpdate(loan);

        return loanRepository.save(loan);
    }
}
