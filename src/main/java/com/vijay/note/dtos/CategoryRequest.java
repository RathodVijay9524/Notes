package com.vijay.note.dtos;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryRequest {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private boolean isActive;
    private boolean isDeleted;
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdOn;
    private Date updatedOn;
}
