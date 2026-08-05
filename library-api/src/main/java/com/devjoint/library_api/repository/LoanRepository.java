package com.devjoint.library_api.repository;

import com.devjoint.library_api.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}