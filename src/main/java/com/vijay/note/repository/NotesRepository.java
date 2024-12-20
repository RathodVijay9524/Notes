package com.vijay.note.repository;

import com.vijay.note.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes,Integer> {

    // Custom method to find notes by category ID
    List<Notes> findByCategoryId(Integer categoryId);


}
