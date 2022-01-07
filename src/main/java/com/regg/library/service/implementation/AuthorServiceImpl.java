package com.regg.library.service.implementation;

import com.regg.library.model.Author;
import com.regg.library.model.RecordNotFoundException;
import com.regg.library.repo.AuthorRepo;
import com.regg.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepo authorRepo;

    @Override
    public Author create(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Collection<Author> list(int limit) {
        return authorRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Author get(Long id) throws RecordNotFoundException {
        authorRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Author"));
        return authorRepo.findById(id).get();
    }

    @Override
    public Author update(Author author, Long id) throws RecordNotFoundException {
        authorRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Author"));
        Author authorToUpdate = authorRepo.getOne(id);
        authorToUpdate.setName(author.getName());
        authorToUpdate.setNationality(author.getNationality());
        return authorRepo.save(authorToUpdate);
    }

    @Override
    public Boolean delete(Long id) throws RecordNotFoundException {
        authorRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Author"));
        authorRepo.deleteById(id);
        return true;
    }

    public String getName(Long id) throws RecordNotFoundException {
        authorRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id,"Author"));
        return authorRepo.findNameById(id);
    }
}
