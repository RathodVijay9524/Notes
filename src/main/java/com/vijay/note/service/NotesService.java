package com.vijay.note.service;

import com.vijay.note.dtos.NotesRequest;
import com.vijay.note.dtos.NotesResponse;
import com.vijay.note.entity.Notes;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface NotesService extends iCrudService<NotesRequest, NotesResponse,Integer>{

    public List<NotesResponse> getNotesByCategoryId(Integer categoryId);


}
