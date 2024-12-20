package com.vijay.note.service.impl;

import com.vijay.note.dtos.CategoryRequest;
import com.vijay.note.dtos.CategoryResponse;
import com.vijay.note.entity.Category;
import com.vijay.note.exceptions.ResourceNotFoundException;
import com.vijay.note.repository.CategoryRepository;
import com.vijay.note.service.CategoryService;
import com.vijay.note.util.mapper.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LogManager.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates a new category.
     *
     * @param request The category request data.
     * @return A CompletableFuture indicating success or failure.
     */
    @Override
    public CompletableFuture<Boolean> create(CategoryRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("Saving category: {}", request.getName());
                Category category = Mapper.toEntity(request, Category.class,"id");
                category.setActive(true);
                category.setDeleted(false);
                categoryRepository.save(category);
                log.info("Category saved successfully");
                return true;
            } catch (Exception ex) {
                log.error("Error saving category: {}", ex.getMessage());
                return false;
            }
        });
    }

    /**
     * Fetches a category by its ID.
     *
     * @param id The ID of the category.
     * @return A CompletableFuture with the category response.
     */
    @Override
    public CompletableFuture<CategoryResponse> getById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Fetching category by ID: {}", id);
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));
            log.info("Category fetched successfully for ID: {}", id);
            return Mapper.toDto(category, CategoryResponse.class);
        });
    }

    /**
     * Fetches all categories.
     *
     * @return A CompletableFuture with a set of category responses.
     */
    @Override
    public CompletableFuture<Set<CategoryResponse>> getAll() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Fetching all categories");
            Set<Category> categories = new HashSet<>(categoryRepository.findAll());
            log.info("Total categories fetched: {}", categories.size());
            return categories.stream()
                    .map(category -> Mapper.toDto(category, CategoryResponse.class))
                    .collect(Collectors.toSet());
        });
    }

    /**
     * Updates an existing category.
     *
     * @param id      The ID of the category to update.
     * @param request The updated category request data.
     * @return A CompletableFuture with the updated category response.
     */
    @Override
    public CompletableFuture<CategoryResponse> update(Long id, CategoryRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Updating category for ID: {}", id);
            Category existingUser = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));
            Category category = Mapper.toEntity(request, Category.class, "id");
            category.setId(existingUser.getId());
            categoryRepository.save(category);
            log.info("Category updated successfully for ID: {}", id);
            return Mapper.toDto(category, CategoryResponse.class);
        });
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     * @return A CompletableFuture indicating success or failure.
     */
    @Override
    public CompletableFuture<Boolean> delete(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<Category> findByCategory = categoryRepository.findById(id);
            if(findByCategory.isPresent()){
                Category category = findByCategory.get();
                category.setDeleted(true);
                categoryRepository.save(category);
                return true;
            }
            return false;
        });
    }

    /**
     * Fetches all active categories.
     *
     * @return A list of active category responses.
     */
    @Override
    public List<CategoryResponse> getActiveCategory() {
        log.info("Fetching active categories");
        List<Category> activeCategories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        log.info("Total active categories fetched: {}", activeCategories.size());
        return activeCategories.stream()
                .map(category -> Mapper.toDto(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Checks if a category with the given name exists.
     *
     * @param name The name of the category.
     * @return True if the category exists, false otherwise.
     */
    @Override
    public boolean existsByName(String name) {
        log.info("Checking existence of category with name: {}", name);
        boolean exists = categoryRepository.existsByName(name);
        log.info("Category existence status for name '{}': {}", name, exists);
        return exists;
    }


    @Override
    public CategoryResponse getActiveAndNonDeletedCategoryById(Long id) {
        Category category = categoryRepository.findByIsActiveTrueAndIsDeletedFalseAndId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));
        return Mapper.toDto(category, CategoryResponse.class);
    }
}
