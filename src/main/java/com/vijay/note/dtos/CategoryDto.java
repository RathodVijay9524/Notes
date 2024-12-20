package com.vijay.note.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private Long id;
    private String name;
    private NotesResponse response;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NotesResponse {
        private Integer id;
        private String title;
        private String description;
    }
}
