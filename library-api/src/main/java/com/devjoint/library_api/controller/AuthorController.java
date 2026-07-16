package com.devjoint.library_api.controller;

import com.devjoint.library_api.dto.AuthorDto;
import com.devjoint.library_api.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<AuthorDto>> getAll() {
        return ResponseEntity.ok(authorService.getAll());
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