package com.devjoint.library_api.controller;

import com.devjoint.library_api.dto.LoanDto;
import com.devjoint.library_api.entity.Loan;
import com.devjoint.library_api.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Loan>> getByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(loanService.getLoansByMember(memberId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Loan>> getActive() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Loan>> getOverdue(@RequestParam(defaultValue = "14") int days) {
        return ResponseEntity.ok(loanService.getOverdueLoans(days));
    }

    @PostMapping("/borrow")
    public ResponseEntity<LoanDto> borrowBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.borrowBook(bookId, memberId));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<LoanDto> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnBook(id));
    }
}