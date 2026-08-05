package com.devjoint.library_api.repository;

import com.devjoint.library_api.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByMember_Id(Long memberId);


    List<Loan> findByReturnDateIsNull();


    @Query("SELECT l FROM Loan l WHERE l.returnDate IS NULL AND l.loanDate < :date")
    List<Loan> findOverdueLoans(@Param("date") LocalDate date);
}