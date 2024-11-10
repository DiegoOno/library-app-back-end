package com.example.library.library_app.application;

import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.domain.repository.BookRepository;
import com.example.library.library_app.domain.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public RecommendationService(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public List<Book> getRecommendations(Long userId) {
        var categories = loanRepository.findDistinctCategoriesByUserId(userId);
        return bookRepository.findRecommendationsByCategoriesAndLibraryUserId(categories, userId);
    }
}
