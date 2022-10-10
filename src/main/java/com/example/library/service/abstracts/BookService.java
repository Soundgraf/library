package com.example.library.service.abstracts;

import com.example.library.model.dto.BookDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    BookDto findBookDtoById(Long id);

    List<BookDto> findAllBookDto();

    BookDto saveBookDto(BookDto bookDto);

    BookDto updateBookDto(BookDto bookDto);

    void deleteBookDtoById(Long id);

    boolean existsBookById(Long id);
}
