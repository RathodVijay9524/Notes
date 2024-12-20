package com.vijay.note.service.impl;

import com.vijay.note.dtos.CategoryResponse;
import com.vijay.note.dtos.NotesRequest;
import com.vijay.note.dtos.NotesResponse;
import com.vijay.note.entity.Category;
import com.vijay.note.entity.Notes;
import com.vijay.note.exceptions.ResourceNotFoundException;
import com.vijay.note.repository.CategoryRepository;
import com.vijay.note.repository.NotesRepository;
import com.vijay.note.service.NotesService;
import com.vijay.note.util.mapper.Mapper;
import com.vijay.note.util.pegination.PaginationHelper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
@Service
public class NotesServiceImpl implements NotesService {

    private final CategoryRepository categoryRepository;
    private final NotesRepository notesRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public NotesServiceImpl(CategoryRepository categoryRepository, NotesRepository notesRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.notesRepository = notesRepository;
        this.modelMapper = modelMapper;
    }
    @Transactional
    @Override
    public CompletableFuture<Boolean> create(NotesRequest request) {
        return CompletableFuture.supplyAsync(()->{
            Category category = categoryRepository.findByIsActiveTrueAndIsDeletedFalseAndId(Long.valueOf(request.getCategory()
                    .getId())).orElseThrow(() -> new ResourceNotFoundException("CATEGORY", "ID", request.getId()));
            Notes notes = modelMapper.map(request, Notes.class);
            notes.setCategory(category);
            notesRepository.save(notes);
            if (!ObjectUtils.isEmpty(notes)) {
                return true;
            }
            return false;
        });

    }


    @Override
    public CompletableFuture<NotesResponse> getById(Integer integer) {
        return null;
    }

    @Override
    public CompletableFuture<Set<NotesResponse>> getAll() {
        List<Notes> notes = notesRepository.findAll();
        return CompletableFuture.supplyAsync(()->{
            return notes.stream().map(note -> modelMapper.map(note, NotesResponse.class))
                    .collect(Collectors.toSet());
        });
    }

    @Override
    public CompletableFuture<NotesResponse> update(Integer integer, NotesRequest request) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> delete(Integer integer) {
        return null;
    }

    @Override
    public List<NotesResponse> getNotesByCategoryId(Integer categoryId) {
        List<Notes> notes = notesRepository.findByCategoryId(categoryId);
        return notes.stream()
                .map(note -> modelMapper.map(note, NotesResponse.class))
                .collect(Collectors.toList());
    }


}
