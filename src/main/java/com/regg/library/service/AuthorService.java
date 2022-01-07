package com.regg.library.service;

import com.regg.library.model.Author;
import com.regg.library.model.RecordNotFoundException;

import java.util.Collection;

public interface AuthorService {
    Author create(Author author);
    Collection<Author> list(int limit);
    Author get(Long id) throws RecordNotFoundException;
    Author update(Author author, Long id) throws RecordNotFoundException;
    Boolean delete(Long id) throws RecordNotFoundException;
}
