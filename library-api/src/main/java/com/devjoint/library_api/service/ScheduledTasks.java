package com.devjoint.library_api.service;

import com.devjoint.library_api.entity.Loan;
import com.devjoint.library_api.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {

    private final LoanRepository loanRepository;

    @Scheduled(cron = "0 0 2 * * *")
    public void checkOverdueLoans() {
        log.info("Günlük gecikmiş borc yoxlaması başladı: {}", LocalDate.now());

        LocalDate cutoffDate = LocalDate.now().minusDays(14);
        List<Loan> overdueLoans = loanRepository.findOverdueLoans(cutoffDate);

        int updated = 0;
        for (Loan loan : overdueLoans) {
            if (!loan.isOverdue()) {
                loan.setOverdue(true);
                loanRepository.save(loan);
                updated++;
            }
        }

        log.info("Tapıldı: {} gecikmiş borc, yeniləndi: {}", overdueLoans.size(), updated);
    }
}