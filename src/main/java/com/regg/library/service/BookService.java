package com.regg.library.service;

import com.regg.library.model.Book;
import com.regg.library.model.RecordNotFoundException;

import java.util.Collection;

public interface BookService {
    Book create(Book book);
    Collection<Book> list(int limit);
    Book get(Long id) throws RecordNotFoundException;
    Book update(Book book, Long id) throws RecordNotFoundException;
    Boolean delete(Long id) throws RecordNotFoundException;
}
