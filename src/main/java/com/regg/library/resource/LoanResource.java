package com.regg.library.resource;

import com.regg.library.model.Loan;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.model.Response;
import com.regg.library.service.implementation.LoanServiceImpl;
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
@RequestMapping("/loan")
public class LoanResource {
    
    @Autowired
    private final LoanServiceImpl loanService;

    public LoanResource(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getLoans(){
        Map<String, Collection<Loan>> myMap = new HashMap<>();
        myMap.put("loans", loanService.list(5000));
        return response(myMap,"Returns retrieved", OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveLoan(@RequestBody @Valid Loan loan){
        Map<String, Loan> myMap = new HashMap<>();
        myMap.put("loan", loanService.create(loan));
        return response(myMap,"Loan created", OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getLoan(@PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Loan> myMap = new HashMap<>();
        try{
            myMap.put("loan", loanService.get(id));
            return response(myMap,"Loan retrieved", OK);
        }catch (RecordNotFoundException e){
            myMap.put("loan", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateLoan(@RequestBody Loan loan, @PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Loan> myMap = new HashMap<>();
        try{
            myMap.put("loan", loanService.update(loan,id));
            return response(myMap,"Loan updated", OK);
        }catch (RecordNotFoundException e){
            myMap.put("loan", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteLoan(@PathVariable("id") Long id) {
        Map<String, Boolean> myMap = new HashMap<>();
        try{
            myMap.put("delete", loanService.delete(id));
        } catch (DataIntegrityViolationException e){
            return response(myMap,"DataIntegrity violation: " + e.getMessage(), BAD_REQUEST);
        } catch (ConstraintViolationException e){
            return response(myMap,"Constraint violation: " + e.getMessage(), BAD_REQUEST);
        } catch (RecordNotFoundException e){
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
        return response(myMap,"Loan deleted", OK);
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
