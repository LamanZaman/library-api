package com.devjoint.library_api.service;

import com.devjoint.library_api.dto.AuthorDto;
import com.devjoint.library_api.entity.Author;
import com.devjoint.library_api.exception.ResourceNotFoundException;
import com.devjoint.library_api.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    // CREATE
    public AuthorDto create(AuthorDto dto) {
        Author author = new Author();
        author.setFullName(dto.getFullName());
        author.setNationality(dto.getNationality());

        Author saved = authorRepository.save(author);
        return toDto(saved);
    }


    public List<AuthorDto> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public AuthorDto getById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return toDto(author);
    }

    // UPDATE
    public AuthorDto update(Long id, AuthorDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        author.setFullName(dto.getFullName());
        author.setNationality(dto.getNationality());

        Author updated = authorRepository.save(author);
        return toDto(updated);
    }

    // DELETE
    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }


    private AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName(), author.getNationality());
    }
}