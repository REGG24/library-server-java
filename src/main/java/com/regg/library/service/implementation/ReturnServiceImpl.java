package com.regg.library.service.implementation;

import com.regg.library.model.RecordNotFoundException;
import com.regg.library.model.Return;
import com.regg.library.repo.ReturnRepo;
import com.regg.library.service.ReturnService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ReturnServiceImpl implements ReturnService {

    private final ReturnRepo returnRepo;

    public ReturnServiceImpl(ReturnRepo returnRepo) {
        this.returnRepo = returnRepo;
    }

    @Override
    public Return create(Return return_) {
        return returnRepo.save(return_);
    }

    @Override
    public Collection<Return> list(int limit) {
        return returnRepo.findAll(PageRequest.of(0,5000)).toList();
    }

    @Override
    public Return get(Long id) throws RecordNotFoundException {
        returnRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Return"));
        return returnRepo.findById(id).get();
    }

    @Override
    public Return update(Return return_, Long id) throws RecordNotFoundException {
        returnRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Return"));
        Return returnToUpdate = returnRepo.getOne(id);
        returnToUpdate.setId_loan(return_.getId_loan());
        returnToUpdate.setDate(return_.getDate());
        return returnRepo.save(returnToUpdate);
    }

    @Override
    public Boolean delete(Long id) throws RecordNotFoundException {
        returnRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Return"));
        returnRepo.deleteById(id);
        return true;
    }
}
