package com.vijay.note.repository;

import com.vijay.note.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    // Custom method to check all Active Category
    List<Category> findByIsActive(boolean b);

    // Custom method to check if a Category exists by name
    boolean existsByName(String name);

    List<Category> findByIsActiveTrueAndIsDeletedFalse();

    Optional<Category> findByIsActiveTrueAndIsDeletedFalseAndId(Long id);

    Optional<Category> findByIdAndIsDeletedFalse(Integer id);

    List<Category> findByIsDeletedFalse();
}
