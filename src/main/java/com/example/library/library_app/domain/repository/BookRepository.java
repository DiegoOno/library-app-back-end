package com.example.library.library_app.domain.repository;

import com.example.library.library_app.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    @Query(value = "SELECT b.* FROM book b WHERE b.category IN :categories AND b.id NOT IN (SELECT l.book_id FROM loan l WHERE l.library_user_id = :libraryUserId)", nativeQuery = true)
    List<Book> findRecommendationsByCategoriesAndLibraryUserId(@Param("categories") List<String> categories, @Param("libraryUserId") Long libraryUserId);
}