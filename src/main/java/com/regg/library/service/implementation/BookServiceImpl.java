package com.regg.library.service.implementation;

import com.regg.library.model.Book;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.repo.BookRepo;
import com.regg.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Override
    public Book create(Book book) {
        return this.bookRepo.save(book);
    }

    @Override
    public Collection<Book> list(int limit) {
        return this.bookRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Book get(Long id) throws RecordNotFoundException {
        bookRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Book"));
        return bookRepo.findById(id).get();
    }

    @Override
    public Book update(Book book, Long id) throws RecordNotFoundException {
        bookRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Book"));
        Book bookToUpdate = bookRepo.getOne(id);
        bookToUpdate.setName(book.getName());
        bookToUpdate.setDescription(book.getDescription());
        bookToUpdate.setPrice(book.getPrice());
        bookToUpdate.setStock(book.getStock());
        bookToUpdate.setId_author(book.getId_author());
        return bookRepo.save(bookToUpdate);
    }

    @Override
    public Boolean delete(Long id) throws RecordNotFoundException {
        bookRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Book"));
        bookRepo.deleteById(id);
        return true;
    }

}
