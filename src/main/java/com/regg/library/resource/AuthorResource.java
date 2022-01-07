package com.regg.library.resource;

import com.regg.library.model.Author;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.model.Response;
import com.regg.library.service.implementation.AuthorServiceImpl;
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
@RequestMapping("/author")
public class AuthorResource {
    @Autowired
    private final AuthorServiceImpl authorService;

    public AuthorResource(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getAuthors(){
        Map<String, Collection<Author>> myMap = new HashMap<>();
        myMap.put("authors", authorService.list(5000));
        return response(myMap,"Authors retrieved", OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveAuthor(@RequestBody @Valid Author author){
        Map<String, Author> myMap = new HashMap<>();
        myMap.put("author", authorService.create(author));
        return response(myMap,"Author successfully created!", OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getAuthor(@PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Author> myMap = new HashMap<>();
        try{
            myMap.put("author", authorService.get(id));
            return response(myMap,"Author retrieved", OK);
        }catch (RecordNotFoundException e){
            myMap.put("author", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateAuthor(@RequestBody Author author, @PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, Author> myMap = new HashMap<>();
        try{
            myMap.put("author", authorService.update(author,id));
            return response(myMap,"Author successfully updated!", OK);
        }catch (RecordNotFoundException e){
            myMap.put("author", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteAuthor(@PathVariable("id") Long id) {
        Map<String, Boolean> myMap = new HashMap<>();
        try{
            myMap.put("delete", authorService.delete(id));
        } catch (DataIntegrityViolationException e){
            return response(myMap,"DataIntegrity violation: " + e.getMessage(), BAD_REQUEST);
        } catch (ConstraintViolationException e){
            return response(myMap,"Constraint violation: " + e.getMessage(), BAD_REQUEST);
        } catch (RecordNotFoundException e){
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
        return response(myMap,"Author successfully deleted!", OK);
    }

    @GetMapping("/getName/{id}")
    public ResponseEntity<Response> getAuthorName(@PathVariable("id") Long id) throws RecordNotFoundException {
        Map<String, String> myMap = new HashMap<>();
        try{
            myMap.put("author", authorService.getName(id));
            return response(myMap,"Author retrieved", OK);
        }catch (RecordNotFoundException e){
            myMap.put("author", null);
            return response(myMap,e.getMessage(), NOT_FOUND);
        }
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
