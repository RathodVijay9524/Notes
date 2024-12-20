package com.vijay.note.service;

import com.vijay.note.dtos.CategoryRequest;
import com.vijay.note.dtos.CategoryResponse;
import com.vijay.note.entity.Category;

import java.util.List;

public interface CategoryService extends iCrudService<CategoryRequest, CategoryResponse,Long>{


    public List<CategoryResponse> getActiveCategory();
    boolean existsByName(String name);
    public CategoryResponse getActiveAndNonDeletedCategoryById(Long id);
}
