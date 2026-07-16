package com.devjoint.library_api.controller;

import com.devjoint.library_api.dto.MemberDto;
import com.devjoint.library_api.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping
    public ResponseEntity<MemberDto> create( @Valid @RequestBody MemberDto dto) {
        MemberDto created = memberService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping
    public ResponseEntity<List<MemberDto>> getAll() {
        return ResponseEntity.ok(memberService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> update(@PathVariable Long id,@Valid @RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}