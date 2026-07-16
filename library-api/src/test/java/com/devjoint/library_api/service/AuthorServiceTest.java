package com.devjoint.library_api.service;

import com.devjoint.library_api.dto.AuthorDto;
import com.devjoint.library_api.entity.Author;
import com.devjoint.library_api.exception.ResourceNotFoundException;
import com.devjoint.library_api.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void create_shouldSaveAndReturnAuthorDto() {

        AuthorDto inputDto = new AuthorDto(null, "George Orwell", "British");
        Author savedAuthor = new Author(1L, "George Orwell", "British", null);

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);


        AuthorDto result = authorService.create(inputDto);


        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("George Orwell", result.getFullName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void getById_shouldReturnAuthorDto_whenAuthorExists() {

        Author author = new Author(1L, "George Orwell", "British", null);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));


        AuthorDto result = authorService.getById(1L);


        assertEquals("George Orwell", result.getFullName());
    }

    @Test
    void getById_shouldThrowException_whenAuthorNotFound() {

        when(authorRepository.findById(99L)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> authorService.getById(99L));
    }

    @Test
    void delete_shouldCallRepository_whenAuthorExists() {

        when(authorRepository.existsById(1L)).thenReturn(true);


        authorService.delete(1L);


        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_shouldThrowException_whenAuthorNotFound() {

        when(authorRepository.existsById(99L)).thenReturn(false);


        assertThrows(ResourceNotFoundException.class, () -> authorService.delete(99L));
    }
}