package com.example.library.library_app.domain;

import com.example.library.library_app.domain.enums.LoanStatus;
import com.example.library.library_app.domain.repository.BookRepository;
import com.example.library.library_app.domain.repository.LibraryUserRepository;
import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.domain.entity.Loan;
import com.example.library.library_app.domain.entity.LibraryUser;
import com.example.library.library_app.domain.repository.test.TestLoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class LoanRepositoryTest {
    @Autowired
    private TestLoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryUserRepository libraryUserRepository;

    private LibraryUser user1;

    @BeforeEach
    public void setUp() {
        user1 = new LibraryUser();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPhone("2423565318");
        user1.setRegisterDate(LocalDateTime.now());

        user1 = libraryUserRepository.save(user1); // ID is automatically assigned by the repository

        Book book1 = new Book("Book 1", "Author 1", LocalDate.of(2020, 1, 1), "1234567890", "Fiction");
        Book book2 = new Book("Book 2", "Author 2", LocalDate.of(2021, 2, 2), "0987654321", "Science Fiction");

        book1 = bookRepository.save(book1);
        book2 = bookRepository.save(book2);

        Loan loan1 = new Loan(user1, book1, LocalDateTime.now(), LocalDateTime.now().plusDays(7), LoanStatus.ACTIVE);
        Loan loan2 = new Loan(user1, book2, LocalDateTime.now(), LocalDateTime.now().plusDays(14), LoanStatus.ACTIVE);

        loanRepository.save(loan1);
        loanRepository.save(loan2);
    }

    @Test
    void testFindDistinctCategoriesWithNoLoans() {
        LibraryUser user2 = new LibraryUser();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPhone("9421183424");
        user2 = libraryUserRepository.save(user2);

        List<String> categories = loanRepository.findDistinctCategoriesByUserId(user2.getId());

        assertNotNull(categories);
        assertTrue(categories.isEmpty(), "Expected empty categories, but got: " + categories);
    }

    @Test
    void testFindDistinctCategoriesByUserId() {
        List<String> categories = loanRepository.findDistinctCategoriesByUserId(user1.getId());

        assertNotNull(categories);
        assertEquals(2, categories.size());
        assertTrue(categories.contains("Fiction"));
        assertTrue(categories.contains("Science Fiction"));
    }

}
