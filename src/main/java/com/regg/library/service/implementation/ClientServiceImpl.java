package com.regg.library.service.implementation;

import com.regg.library.model.Client;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.repo.ClientRepo;
import com.regg.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional

public class ClientServiceImpl implements ClientService {
    @Autowired
    private final ClientRepo clientRepo;

    public ClientServiceImpl(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public Client create(Client client) {
        return clientRepo.save(client);
    }

    @Override
    public Collection<Client> list() {
        return clientRepo.findAll();
    }

    @Override
    public Collection<Client> listByName(String name) {
        return clientRepo.listByName(name);
    }

    @Override
    public Client get(Long id) throws RecordNotFoundException {
        clientRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Client"));
        return clientRepo.findById(id).get();
    }

    @Override
    public Client update(Client client,Long id) throws RecordNotFoundException {
        clientRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Client"));
        Client clientToUpdate = clientRepo.getOne(id);
        clientToUpdate.setName(client.getName());
        clientToUpdate.setPhone(client.getPhone());
        clientToUpdate.setAddress(client.getAddress());
        return clientRepo.save(clientToUpdate);
    }

    @Override
    public Boolean delete(Long id) throws RecordNotFoundException {
        clientRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Client"));
        clientRepo.deleteById(id);
        return true;
    }
}
