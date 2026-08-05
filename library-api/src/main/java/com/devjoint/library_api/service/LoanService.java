package com.devjoint.library_api.service;

import com.devjoint.library_api.entity.Loan;
import com.devjoint.library_api.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public List<Loan> getLoansByMember(Long memberId) {
        return loanRepository.findByMember_Id(memberId);
    }

    public List<Loan> getActiveLoans() {
        return loanRepository.findByReturnDateIsNull();
    }

    public List<Loan> getOverdueLoans(int daysThreshold) {
        LocalDate cutoff = LocalDate.now().minusDays(daysThreshold);
        return loanRepository.findOverdueLoans(cutoff);
    }
}