package com.regg.library.resource;

import com.regg.library.model.Return;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.model.Response;
import com.regg.library.service.implementation.ReturnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/returns")
public class ReturnResource {
    
    @Autowired
    private final ReturnServiceImpl returnService;

    public ReturnResource(ReturnServiceImpl returnService) {
        this.returnService = returnService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getReturns(){
        Map<String, Collection<Return>> myMap = new HashMap<>();
        myMap.put("returns", returnService.list(5000));
        return response(myMap,"Returns retrieved", OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveReturn(@RequestBody @Valid Return return_){
        Map<String, Return> myMap = new HashMap<>();
        myMap.put("return", returnService.create(return_));
        return response(myMap,"Return created", OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getReturn(@PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Return> myMap = new HashMap<>();
        try{
            myMap.put("return", returnService.get(id));
            return response(myMap,"Return retrieved", OK);
        }catch (RecordNotFoundException e){
            myMap.put("return_", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateReturn(@RequestBody Return return_, @PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Return> myMap = new HashMap<>();
        try{
            myMap.put("return", returnService.update(return_,id));
            return response(myMap,"Return updated", OK);
        }catch (RecordNotFoundException e){
            myMap.put("return_", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteReturn(@PathVariable("id") Long id) {
        Map<String, Boolean> myMap = new HashMap<>();
        try{
            myMap.put("delete", returnService.delete(id));
        } catch (DataIntegrityViolationException e){
            return response(myMap,"DataIntegrity violation: " + e.getMessage(), BAD_REQUEST);
        } catch (ConstraintViolationException e){
            return response(myMap,"Constraint violation: " + e.getMessage(), BAD_REQUEST);
        } catch (RecordNotFoundException e){
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
        return response(myMap,"Return deleted", OK);
    }

    public ResponseEntity<Response> response(Map<?,?> myMap, String message, HttpStatus status){
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
