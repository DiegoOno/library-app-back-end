package com.example.library.library_app.application;

import com.example.library.library_app.domain.entity.Book;
import com.example.library.library_app.domain.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Book update(Book book) {
        Book bookEntity = bookRepository.findById(book.getId()).orElseThrow(() -> new RuntimeException("Book not found"));
        bookEntity.mergeForUpdate(book);

        return bookRepository.save(bookEntity);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.deleteById(book.getId());
    }
}
