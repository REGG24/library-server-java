package com.regg.library.resource;


import com.regg.library.model.Client;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.model.Response;
import com.regg.library.service.implementation.ClientServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/client")
public class ClientResource {

    private final ClientServiceImpl clientService;

    public ClientResource(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getClients(){
        Map<String, Collection<Client>> myMap = new HashMap<>();
        myMap.put("clients", clientService.list());
        return response(myMap,"Clients retrieved", OK);
    }

    @GetMapping("/list/{name}")
    public ResponseEntity<Response> getClientsByName(@PathVariable("name") String name){
        Map<String, Collection<Client>> myMap = new HashMap<>();
        myMap.put("clients", clientService.listByName(name));
        return response(myMap,"Clients retrieved", OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveClient(@RequestBody @Valid Client client){
        Map<String, Client> myMap = new HashMap<>();
        myMap.put("client", clientService.create(client));
        return response(myMap,"Client successfully created!", OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getClient(@PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Client> myMap = new HashMap<>();
        try{
            myMap.put("client", clientService.get(id));
            return response(myMap,"Client retrieved", OK);
        }catch (RecordNotFoundException e){
            myMap.put("client", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateClient(@RequestBody Client client,@PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Client> myMap = new HashMap<>();
        try{
            myMap.put("client", clientService.update(client,id));
            return response(myMap,"Client successfully updated!", OK);
        }catch (RecordNotFoundException e){
            myMap.put("client", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteClient(@PathVariable("id") Long id) {
        Map<String, Boolean> myMap = new HashMap<>();
            try{
                myMap.put("delete", clientService.delete(id));
            } catch (DataIntegrityViolationException e){
                return response(myMap,"DataIntegrity violation: " + e.getMessage(), BAD_REQUEST);
            } catch (ConstraintViolationException e){
                return response(myMap,"Constraint violation: " + e.getMessage(), BAD_REQUEST);
            } catch (RecordNotFoundException e){
                return response(myMap,e.getMessage(), NOT_FOUND);
            }
            return response(myMap,"Client successfully deleted!", OK);
    }

    public ResponseEntity<Response> response(Map<?,?> myMap,String message,HttpStatus status){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(myMap)
                        .message(message)
                        .status(status)
                        .statusCode(status.value())
                        .build()
        );
    }

}
