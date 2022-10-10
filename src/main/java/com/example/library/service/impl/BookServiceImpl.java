package com.example.library.service.impl;

import com.example.library.model.converter.BookMapper;
import com.example.library.model.dto.BookDto;
import com.example.library.model.entity.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.abstracts.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto findBookDtoById(Long id) {
        BookDto bookDto = bookMapper.bookToDto(bookRepository.findById(id).get());
        bookDto.setAuthorSurname(bookRepository.authorSurname(id));
        return bookDto;
    }

    @Override
    public List<BookDto> findAllBookDto() {
        return bookRepository.findAll().stream()
                .map(bookMapper::bookToDto)
                .toList();
    }

    @Override
    public BookDto saveBookDto(BookDto bookDto) {
        Book book = bookRepository.save(bookMapper.dtoToBook(bookDto));
        return bookMapper.bookToDto(book);
    }

    @Override
    public BookDto updateBookDto(BookDto bookDto) {
        Book book = bookRepository.save(bookMapper.dtoToBook(bookDto));
        return bookMapper.bookToDto(book);
    }

    @Override
    public void deleteBookDtoById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean existsBookById(Long id) {
        return bookRepository.existsById(id);
    }
}
