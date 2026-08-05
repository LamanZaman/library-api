package com.devjoint.library_api.repository;

import com.devjoint.library_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {
    List<Book> findByCategories_NameIgnoreCase(String categoryName);


    @Query("SELECT b FROM Book b WHERE b.author.id = :authorId AND LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchByAuthorAndTitleKeyword(@Param("authorId") Long authorId, @Param("keyword") String keyword);


    @Query(value = "SELECT DISTINCT b.* FROM books b " +
            "JOIN loans l ON l.book_id = b.id " +
            "WHERE l.return_date IS NULL", nativeQuery = true)
    List<Book> findCurrentlyLoanedBooks();
}
