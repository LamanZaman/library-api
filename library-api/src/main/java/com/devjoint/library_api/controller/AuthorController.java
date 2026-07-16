package com.devjoint.library_api.controller;

import com.devjoint.library_api.dto.AuthorDto;
import com.devjoint.library_api.service.AuthorService;
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
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    @PostMapping
    public ResponseEntity<AuthorDto> create( @Valid @RequestBody AuthorDto dto) {
        AuthorDto created = authorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<AuthorDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(authorService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> update(@PathVariable Long id, @Valid @RequestBody AuthorDto dto) {
        return ResponseEntity.ok(authorService.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}