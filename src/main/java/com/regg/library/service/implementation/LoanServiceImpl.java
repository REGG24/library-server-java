package com.regg.library.service.implementation;

import com.regg.library.model.Loan;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.repo.LoanRepo;
import com.regg.library.service.LoanService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanRepo loanRepo;

    public LoanServiceImpl(LoanRepo loanRepo) {
        this.loanRepo = loanRepo;
    }

    @Override
    public Loan create(Loan loan) {
        return loanRepo.save(loan);
    }

    @Override
    public Collection<Loan> list(int limit) {
        return loanRepo.findAll(PageRequest.of(0,5000)).toList();
    }

    @Override
    public Loan get(Long id) throws RecordNotFoundException {
        loanRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Loan"));
        return loanRepo.findById(id).get();
    }

    @Override
    public Loan update(Loan loan, Long id) throws RecordNotFoundException {
        loanRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Loan"));
        Loan loanToUpdate = loanRepo.getOne(id);
        loanToUpdate.setId_book(loan.getId_book());
        loanToUpdate.setId_employee(loan.getId_employee());
        loanToUpdate.setId_client(loan.getId_client());
        loanToUpdate.setDate(loan.getDate());
        return loanRepo.save(loanToUpdate);
    }

    @Override
    public Boolean delete(Long id) throws RecordNotFoundException {
        loanRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Loan"));
        loanRepo.deleteById(id);
        return true;
    }
}
