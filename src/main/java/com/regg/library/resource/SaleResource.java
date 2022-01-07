package com.regg.library.resource;

import com.regg.library.model.Sale;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.model.Response;
import com.regg.library.service.implementation.SaleServiceImpl;
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
@RequestMapping("/sale")
public class SaleResource {

    @Autowired
    private final SaleServiceImpl saleService;

    public SaleResource(SaleServiceImpl saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getSales(){
        Map<String, Collection<Sale>> myMap = new HashMap<>();
        myMap.put("sales", saleService.list(5000));
            return response(myMap,"Sales retrieved", OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveSale(@RequestBody @Valid Sale sale){
        System.out.println("sale: "+sale);
        Map<String, Sale> myMap = new HashMap<>();
        myMap.put("sale", saleService.create(sale));
        return response(myMap,"Sale created", OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getSale(@PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Sale> myMap = new HashMap<>();
        try{
            myMap.put("sale", saleService.get(id));
            return response(myMap,"Sale retrieved", OK);
        }catch (RecordNotFoundException e){
            myMap.put("sale", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateSale(@RequestBody Sale sale, @PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Sale> myMap = new HashMap<>();
        try{
            myMap.put("sale", saleService.update(sale,id));
            return response(myMap,"Sale updated", OK);
        }catch (RecordNotFoundException e){
            myMap.put("sale", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteSale(@PathVariable("id") Long id) {
        Map<String, Boolean> myMap = new HashMap<>();
        try{
            myMap.put("delete", saleService.delete(id));
        } catch (DataIntegrityViolationException e){
            return response(myMap,"DataIntegrity violation: " + e.getMessage(), BAD_REQUEST);
        } catch (ConstraintViolationException e){
            return response(myMap,"Constraint violation: " + e.getMessage(), BAD_REQUEST);
        } catch (RecordNotFoundException e){
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
        return response(myMap,"Sale deleted", OK);
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
