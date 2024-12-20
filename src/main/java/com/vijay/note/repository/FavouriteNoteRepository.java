package com.vijay.note.repository;

import java.util.List;


import com.vijay.note.entity.FavouriteNote;
import org.springframework.data.jpa.repository.JpaRepository;



public interface FavouriteNoteRepository extends JpaRepository<FavouriteNote, Integer> {

    List<FavouriteNote> findByUserId(int userId);

}