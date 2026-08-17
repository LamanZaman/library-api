package com.devjoint.library_api.service;

import com.devjoint.library_api.dto.LoanDto;
import com.devjoint.library_api.entity.Book;
import com.devjoint.library_api.entity.Loan;
import com.devjoint.library_api.entity.Member;
import com.devjoint.library_api.exception.ResourceNotFoundException;
import com.devjoint.library_api.repository.BookRepository;
import com.devjoint.library_api.repository.LoanRepository;
import com.devjoint.library_api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public LoanDto borrowBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is already loaned out");
        }


        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setLoanDate(LocalDate.now());
        Loan savedLoan = loanRepository.save(loan);


        book.setAvailable(false);
        bookRepository.save(book);
        emailService.sendLoanConfirmationEmail(member.getEmail(), book.getTitle());
        return toDto(savedLoan);
    }

    @Transactional
    public LoanDto returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + loanId));

        if (loan.getReturnDate() != null) {
            throw new IllegalStateException("Book already returned");
        }

        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return toDto(loan);
    }

    public List<Loan> getLoansByMember(Long memberId) {
        return loanRepository.findByMember_Id(memberId);
    }

    public List<Loan> getActiveLoans() {
        return loanRepository.findActiveLoansWithDetails();
    }

    public List<Loan> getOverdueLoans(int daysThreshold) {
        LocalDate cutoff = LocalDate.now().minusDays(daysThreshold);
        return loanRepository.findOverdueLoans(cutoff);
    }

    private LoanDto toDto(Loan loan) {
        return new LoanDto(
                loan.getId(),
                loan.getBook().getId(),
                loan.getBook().getTitle(),
                loan.getMember().getId(),
                loan.getMember().getFullName(),
                loan.getLoanDate(),
                loan.getReturnDate()
        );
    }
    private final EmailService emailService;

}