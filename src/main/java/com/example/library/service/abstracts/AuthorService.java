package com.example.library.service.abstracts;

import com.example.library.model.dto.AuthorDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    AuthorDto findAuthorDtoById(Long id);

    List<AuthorDto> findAllAuthorDto();

    AuthorDto saveAuthorDto(AuthorDto authorDto);

    AuthorDto updateAuthorDto(AuthorDto authorDto);

    void deleteAuthorDtoById(Long id);

    boolean existsAuthorById(Long id);
}
