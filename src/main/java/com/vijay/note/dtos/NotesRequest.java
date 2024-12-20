package com.vijay.note.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotesRequest {

    private Integer id;

    private String title;

    private String description;

    private CategoryResponse category;

    private Integer createdBy;

    private Date createdOn;

    private Integer updatedBy;

    private Date updatedOn;

   /* @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryResponse {
        private Integer id;
        private String name;
    }*/
}
