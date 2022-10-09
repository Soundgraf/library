package com.example.library.model.dto;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private Integer year;
    private String authorSurname;
}
