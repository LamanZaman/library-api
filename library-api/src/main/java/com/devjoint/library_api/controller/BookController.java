package com.devjoint.library_api.controller;

import com.devjoint.library_api.dto.BookDto;
import com.devjoint.library_api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @PostMapping
    public ResponseEntity<BookDto> create( @Valid @RequestBody BookDto dto) {
        BookDto created = bookService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping
    public ResponseEntity<Page<BookDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(bookService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id,@Valid @RequestBody BookDto dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/by-category")
    public ResponseEntity<List<BookDto>> getByCategory(@RequestParam String category) {
        return ResponseEntity.ok(bookService.getByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchByAuthorAndKeyword(
            @RequestParam Long authorId,
            @RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchByAuthorAndKeyword(authorId, keyword));
    }

    @GetMapping("/currently-loaned")
    public ResponseEntity<List<BookDto>> getCurrentlyLoanedBooks() {
        return ResponseEntity.ok(bookService.getCurrentlyLoanedBooks());
    }


    @GetMapping("/search-dynamic")
    public ResponseEntity<List<BookDto>> searchDynamic(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(bookService.searchBooks(title, author, category));
    }
}