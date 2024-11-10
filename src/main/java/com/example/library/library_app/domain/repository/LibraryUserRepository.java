package com.example.library.library_app.domain.repository;

import com.example.library.library_app.domain.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
}