package com.regg.library.repo;

import com.regg.library.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepo extends JpaRepository<Sale,Long> {
}
