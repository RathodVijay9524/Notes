package com.vijay.note.controllers;

import com.vijay.note.dtos.CategoryRequest;
import com.vijay.note.dtos.CategoryResponse;
import com.vijay.note.service.CategoryService;
import com.vijay.note.util.ExceptionUtil;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Creates a new category.
     *
     * @param request The category request data.
     * @return A CompletableFuture with the response entity.
     */
    @PostMapping
    public CompletableFuture<ResponseEntity<?>> saveCategory(@Valid @RequestBody CategoryRequest request) {
        log.info("Received request to save a new category: {}", request.getName());
        return categoryService.create(request)
                .thenApply(success -> {
                    log.info("Category save operation completed with status: {}", success);
                    return success
                            ? ExceptionUtil.createBuildResponseMessage("Category saved successfully", HttpStatus.CREATED)
                            : ExceptionUtil.createErrorResponseMessage("Failed to save category", HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    /**
     * Fetches a category by its ID.
     *
     * @param id The ID of the category.
     * @return A CompletableFuture with the response entity.
     */
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> getCategoryById(@PathVariable Long id) {
        log.info("Received request to fetch category by ID: {}", id);
        return categoryService.getById(id)
                .thenApply(categoryResponse -> {
                    log.info("Category fetched successfully for ID: {}", id);
                    return ExceptionUtil.createBuildResponse(categoryResponse, HttpStatus.OK);
                });
    }

    /**
     * Fetches all categories.
     *
     * @return A CompletableFuture with the response entity.
     */
    @GetMapping
    public CompletableFuture<ResponseEntity<?>> getAllCategories() {
        log.info("Received request to fetch all categories.");
        return categoryService.getAll()
                .thenApply(categoryResponses -> {
                    log.info("Fetched {} categories successfully.", categoryResponses.size());
                    return ExceptionUtil.createBuildResponse(categoryResponses, HttpStatus.OK);
                });
    }

    /**
     * Updates a category by its ID.
     *
     * @param id      The ID of the category to update.
     * @param request The updated category request data.
     * @return A CompletableFuture with the response entity.
     */
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        log.info("Received request to update category for ID: {}", id);
        return categoryService.update(id, request)
                .thenApply(updatedCategory -> {
                    log.info("Category updated successfully for ID: {}", id);
                    return ExceptionUtil.createBuildResponse(updatedCategory, HttpStatus.OK);
                });
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     * @return A CompletableFuture with the response entity.
     */
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> deleteCategory(@PathVariable Long id) {
        log.info("Received request to delete category for ID: {}", id);
        return categoryService.delete(id)
                .thenApply(success -> {
                    log.info("Category delete operation completed with status: {}", success);
                    return success
                            ? ExceptionUtil.createBuildResponseMessage("Category deleted successfully", HttpStatus.OK)
                            : ExceptionUtil.createErrorResponseMessage("Failed to delete category", HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    /**
     * Fetches all active categories.
     *
     * @return A ResponseEntity with the active categories.
     */
    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategories() {
        log.info("Received request to fetch active categories.");
        List<CategoryResponse> activeCategories = categoryService.getActiveCategory();
        log.info("Fetched {} active categories successfully.", activeCategories.size());
        return ExceptionUtil.createBuildResponse(activeCategories, HttpStatus.OK);
    }

    /**
     * Checks if a category exists by name.
     *
     * @param name The name of the category.
     * @return A ResponseEntity with the existence status.
     */
    @GetMapping("/exists/{name}")
    public ResponseEntity<?> existsByName(@PathVariable String name) {
        log.info("Received request to check if category exists by name: {}", name);
        boolean exists = categoryService.existsByName(name);
        log.info("Existence check for category '{}': {}", name, exists);
        return ExceptionUtil.createBuildResponseMessage(String.valueOf(exists), HttpStatus.OK);
    }

    @GetMapping("/active/{id}")
    public ResponseEntity<?> getActiveCategoryById(@PathVariable Long id) {
        CategoryResponse category = categoryService.getActiveAndNonDeletedCategoryById(id);
        return ExceptionUtil.createBuildResponse(category, HttpStatus.OK);
    }
}

