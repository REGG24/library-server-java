package com.regg.library.repo;

import com.regg.library.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ClientRepo extends JpaRepository<Client,Long> {
    @Query("SELECT c FROM Client c WHERE c.name LIKE %?1%")
    Collection<Client> listByName(String name);
}
