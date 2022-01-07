package com.regg.library.service;

import com.regg.library.model.RecordNotFoundException;
import com.regg.library.model.Loan;

import java.util.Collection;

public interface LoanService {
    Loan create(Loan loan);
    Collection<Loan> list(int limit);
    Loan get(Long id) throws RecordNotFoundException;
    Loan update(Loan loan, Long id) throws RecordNotFoundException;
    Boolean delete(Long id) throws RecordNotFoundException;
}
