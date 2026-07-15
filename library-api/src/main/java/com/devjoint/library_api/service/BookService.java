package com.devjoint.library_api.service;

import com.devjoint.library_api.dto.BookDto;
import com.devjoint.library_api.entity.Author;
import com.devjoint.library_api.entity.Book;
import com.devjoint.library_api.repository.AuthorRepository;
import com.devjoint.library_api.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    // CREATE
    public BookDto create(BookDto dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + dto.getAuthorId()));

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setAuthor(author);

        Book saved = bookRepository.save(book);
        return toDto(saved);
    }


    public List<BookDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toDto(book);
    }

    // UPDATE
    public BookDto update(Long id, BookDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + dto.getAuthorId()));

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setAuthor(author);

        Book updated = bookRepository.save(book);
        return toDto(updated);
    }

    // DELETE
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }


    private BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAuthor().getId(),
                book.getAuthor().getFullName()
        );
    }
}