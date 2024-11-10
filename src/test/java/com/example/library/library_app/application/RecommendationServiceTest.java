package com.example.library.library_app.application;

import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.domain.repository.LoanRepository;
import com.example.library.library_app.domain.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecommendationServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    private final Long userId = 1L;

    @Test
    void testGetRecommendations() {
        List<String> categories = List.of("Fiction", "Science Fiction", "Fantasy");

        List<Book> recommendedBooks = List.of(
                new Book("Book 1", "Author 1", LocalDate.of(2020, 1, 1), "1234567890", "Fiction"),
                new Book("Book 2", "Author 2", LocalDate.of(2021, 2, 2), "0987654321", "Science Fiction"),
                new Book("Book 3", "Author 3", LocalDate.of(2022, 3, 3), "1122334455", "Fantasy")
        );

        when(loanRepository.findDistinctCategoriesByUserId(userId)).thenReturn(categories);
        when(bookRepository.findRecommendationsByCategoriesAndLibraryUserId(categories, userId)).thenReturn(recommendedBooks);

        List<Book> recommendations = recommendationService.getRecommendations(userId);

        assertNotNull(recommendations);
        assertEquals(3, recommendations.size());

        assertEquals("Book 1", recommendations.getFirst().getTitle());
        assertEquals("Author 1", recommendations.getFirst().getAuthor());
        assertEquals(LocalDate.of(2020, 1, 1), recommendations.getFirst().getReleaseDate());
        assertEquals("1234567890", recommendations.get(0).getIsbn());
        assertEquals("Fiction", recommendations.get(0).getCategory());

        assertEquals("Book 2", recommendations.get(1).getTitle());
        assertEquals("Author 2", recommendations.get(1).getAuthor());
        assertEquals(LocalDate.of(2021, 2, 2), recommendations.get(1).getReleaseDate());
        assertEquals("0987654321", recommendations.get(1).getIsbn());
        assertEquals("Science Fiction", recommendations.get(1).getCategory());

        assertEquals("Book 3", recommendations.get(2).getTitle());
        assertEquals("Author 3", recommendations.get(2).getAuthor());
        assertEquals(LocalDate.of(2022, 3, 3), recommendations.get(2).getReleaseDate());
        assertEquals("1122334455", recommendations.get(2).getIsbn());
        assertEquals("Fantasy", recommendations.get(2).getCategory());

        verify(loanRepository, times(1)).findDistinctCategoriesByUserId(userId);
        verify(bookRepository, times(1)).findRecommendationsByCategoriesAndLibraryUserId(categories, userId);
    }

    @Test
    void testGetRecommendationsWithNoCategories() {
        List<String> emptyCategories = List.of();
        when(loanRepository.findDistinctCategoriesByUserId(userId)).thenReturn(emptyCategories);
        when(bookRepository.findRecommendationsByCategoriesAndLibraryUserId(emptyCategories, userId)).thenReturn(List.of());

        List<Book> recommendations = recommendationService.getRecommendations(userId);

        assertNotNull(recommendations);
        assertTrue(recommendations.isEmpty());
    }
}
