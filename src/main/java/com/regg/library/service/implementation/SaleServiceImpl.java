package com.regg.library.service.implementation;

import com.regg.library.model.RecordNotFoundException;
import com.regg.library.model.Sale;
import com.regg.library.repo.SaleRepo;
import com.regg.library.service.SaleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepo saleRepo;

    public SaleServiceImpl(SaleRepo saleRepo) {
        this.saleRepo = saleRepo;
    }

    @Override
    public Sale create(Sale sale) {
        return saleRepo.save(sale);
    }

    @Override
    public Collection<Sale> list(int limit) {
        return saleRepo.findAll(PageRequest.of(0,5000)).toList();
    }

    @Override
    public Sale get(Long id) throws RecordNotFoundException {
        saleRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Sale"));
        return saleRepo.findById(id).get();
    }

    @Override
    public Sale update(Sale sale, Long id) throws RecordNotFoundException {
        saleRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Sale"));
        Sale saleToUpdate = saleRepo.getOne(id);
        saleToUpdate.setId_book(sale.getId_book());
        saleToUpdate.setId_employee(sale.getId_employee());
        saleToUpdate.setId_client(sale.getId_client());
        saleToUpdate.setDate(sale.getDate());
        return saleRepo.save(saleToUpdate);
    }

    @Override
    public Boolean delete(Long id) throws RecordNotFoundException {
        saleRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Sale"));
        saleRepo.deleteById(id);
        return true;
    }
}
