package com.regg.library.repo;

import com.regg.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepo extends JpaRepository<Loan,Long> {
}
