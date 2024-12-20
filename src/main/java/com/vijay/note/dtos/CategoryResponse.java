package com.vijay.note.dtos;

import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private boolean isActive;
    private boolean isDeleted;
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdOn;
    private Date updatedOn;
}
