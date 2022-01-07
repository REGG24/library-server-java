package com.regg.library.repo;

import com.regg.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepo extends JpaRepository<Author,Long> {
    @Query("select a.name from Author a where a.id_author = ?1")
    String findNameById(Long id);
}
