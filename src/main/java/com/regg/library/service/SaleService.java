package com.regg.library.service;

import com.regg.library.model.Sale;
import com.regg.library.model.RecordNotFoundException;

import java.util.Collection;

public interface SaleService {
    Sale create(Sale sale);
    Collection<Sale> list(int limit);
    Sale get(Long id) throws RecordNotFoundException;
    Sale update(Sale sale, Long id) throws RecordNotFoundException;
    Boolean delete(Long id) throws RecordNotFoundException;
}
