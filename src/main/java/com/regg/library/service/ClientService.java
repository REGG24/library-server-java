package com.regg.library.service;

import com.regg.library.model.Client;
import com.regg.library.model.RecordNotFoundException;

import java.util.Collection;

public interface ClientService {
    Client create(Client client);
    Collection<Client> list();
    Collection<Client> listByName(String name);
    Client get(Long id) throws RecordNotFoundException;
    Client update(Client client, Long id) throws RecordNotFoundException;
    Boolean delete(Long id) throws RecordNotFoundException;
}
