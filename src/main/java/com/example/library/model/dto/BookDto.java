package com.example.library.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {
    private Long id;
    private String title;
    private Integer year;
    private String authorSurname;
}
