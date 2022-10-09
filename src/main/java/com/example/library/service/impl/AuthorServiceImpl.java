package com.example.library.service.impl;

import com.example.library.model.converter.AuthorMapper;
import com.example.library.model.dto.AuthorDto;
import com.example.library.model.entity.Author;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.abstracts.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto findAuthorDtoById(Long id) {
        return authorMapper.authorToDto(authorRepository.findById(id).get());
    }

    @Override
    public List<AuthorDto> findAllAuthorDto() {
        return authorRepository.findAll().stream()
                .map(authorMapper::authorToDto)
                .toList();
    }

    @Override
    public AuthorDto saveAuthorDto(AuthorDto authorDto) {
        Author author = authorRepository.save(authorMapper.dtoToAuthor(authorDto));
        return authorMapper.authorToDto(author);
    }

    @Override
    public AuthorDto updateAuthorDto(AuthorDto authorDto) {
        Author author = authorRepository.save(authorMapper.dtoToAuthor(authorDto));
        return authorMapper.authorToDto(author);
    }

    @Override
    public void deleteAuthorDtoById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public boolean existsAuthorById(Long id) {
        return authorRepository.existsById(id);
    }
}
