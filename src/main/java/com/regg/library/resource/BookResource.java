package com.regg.library.resource;

import com.regg.library.model.Book;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.model.Response;
import com.regg.library.service.implementation.BookServiceImpl;
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
@RequestMapping("/book")
public class BookResource {

    private final BookServiceImpl bookService;

    public BookResource(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getBooks(){
        Map<String, Collection<Book>> myMap = new HashMap<>();
        myMap.put("books", bookService.list(5000));
        return response(myMap,"Books retrieved", OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveBook(@RequestBody @Valid Book book){
        Map<String, Book> myMap = new HashMap<>();
        myMap.put("book", bookService.create(book));
        return response(myMap,"Book successfully created!", OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getBook(@PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Book> myMap = new HashMap<>();
        try{
            myMap.put("book", bookService.get(id));
            return response(myMap,"Book retrieved", OK);
        }catch (RecordNotFoundException e){
            myMap.put("book", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateBook(@RequestBody Book book, @PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Book> myMap = new HashMap<>();
        try{
            myMap.put("book", bookService.update(book,id));
            return response(myMap,"Book successfully updated!", OK);
        }catch (RecordNotFoundException e){
            myMap.put("book", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBook(@PathVariable("id") Long id) {
        Map<String, Boolean> myMap = new HashMap<>();
        try{
            myMap.put("delete", bookService.delete(id));
        } catch (DataIntegrityViolationException e){
            return response(myMap,"DataIntegrity violation: " + e.getMessage(), BAD_REQUEST);
        } catch (ConstraintViolationException e){
            return response(myMap,"Constraint violation: " + e.getMessage(), BAD_REQUEST);
        } catch (RecordNotFoundException e){
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
        return response(myMap,"Book successfully deleted!", OK);
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
