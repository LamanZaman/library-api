package com.devjoint.library_api.service;

import com.devjoint.library_api.dto.BookDto;
import com.devjoint.library_api.entity.Author;
import com.devjoint.library_api.entity.Book;
import com.devjoint.library_api.exception.ResourceNotFoundException;
import com.devjoint.library_api.repository.AuthorRepository;
import com.devjoint.library_api.repository.BookRepository;
import com.devjoint.library_api.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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


    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::toDto);
    }


    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return toDto(book);
    }

    // UPDATE
    public BookDto update(Long id, BookDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + dto.getAuthorId()));

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setAuthor(author);

        Book updated = bookRepository.save(book);
        return toDto(updated);
    }

    // DELETE
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
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


    public List<BookDto> getByCategory(String categoryName) {
        return bookRepository.findByCategories_NameIgnoreCase(categoryName)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public List<BookDto> searchByAuthorAndKeyword(Long authorId, String keyword) {
        return bookRepository.searchByAuthorAndTitleKeyword(authorId, keyword)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public List<BookDto> getCurrentlyLoanedBooks() {
        return bookRepository.findCurrentlyLoanedBooks()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> searchBooks(String title, String authorName, String category) {
        Specification<Book> spec = Specification.where(BookSpecification.hasTitle(title))
                .and(BookSpecification.hasAuthorName(authorName))
                .and(BookSpecification.hasCategory(category));

        return bookRepository.findAll(spec)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}