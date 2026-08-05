package com.devjoint.library_api.service;

import com.devjoint.library_api.entity.Book;
import com.devjoint.library_api.entity.Member;
import com.devjoint.library_api.repository.BookRepository;
import com.devjoint.library_api.repository.LoanRepository;
import com.devjoint.library_api.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    void borrowBook_shouldThrowException_whenBookAlreadyLoaned() {

        Book book = new Book();
        book.setId(1L);
        book.setAvailable(false);

        Member member = new Member();
        member.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));


        assertThrows(IllegalStateException.class, () -> loanService.borrowBook(1L, 1L));


        verify(loanRepository, never()).save(any());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void borrowBook_shouldThrowException_whenBookNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> loanService.borrowBook(99L, 1L));

        verify(loanRepository, never()).save(any());
    }
}