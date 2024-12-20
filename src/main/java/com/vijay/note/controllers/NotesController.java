package com.vijay.note.controllers;

import com.vijay.note.dtos.NotesRequest;
import com.vijay.note.dtos.NotesResponse;
import com.vijay.note.service.NotesService;

import com.vijay.note.util.ExceptionUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@Log4j2
@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    /**
     * Endpoint to create a new note.
     *
     * @param request The request body containing note details.
     * @return A CompletableFuture with the response entity.
     */
    @PostMapping
    public CompletableFuture<ResponseEntity<?>> createNote(@RequestBody NotesRequest request) {
        return notesService.create(request)
                .thenApply(success -> {
                    if (success) {
                        return ExceptionUtil.createBuildResponseMessage("Note created successfully", HttpStatus.CREATED);
                    }
                    return ExceptionUtil.createBuildResponseMessage("Failed to create note", HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    /**
     * Endpoint to fetch all notes.
     *
     * @return A CompletableFuture with the response entity containing all notes.
     */
    @GetMapping
    public CompletableFuture<ResponseEntity<?>> getAllActiveNotes() {
        return notesService.getAll()
                .thenApply(noteResponses -> {
                    log.info("Fetched {} Notes successfully.", noteResponses.size());
                    return ExceptionUtil.createBuildResponse(noteResponses, HttpStatus.OK);
                });
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getNotesByCategoryId(@PathVariable Integer categoryId) {
        List<NotesResponse> notes = notesService.getNotesByCategoryId(categoryId);
        return ExceptionUtil.createBuildResponse(notes, HttpStatus.OK);
    }

}