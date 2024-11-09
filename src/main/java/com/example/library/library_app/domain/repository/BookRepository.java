package com.example.library.library_app.domain.repository;

import com.example.library.library_app.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{
}