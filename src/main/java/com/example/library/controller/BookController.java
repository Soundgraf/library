package com.example.library.controller;

import com.example.library.model.converter.AuthorMapper;
import com.example.library.model.converter.BookMapper;
import com.example.library.model.dto.AuthorDto;
import com.example.library.model.dto.BookDto;
import com.example.library.model.entity.Book;
import com.example.library.service.abstracts.AuthorService;
import com.example.library.service.abstracts.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Book RestController")
public class BookController {
    public static final String LOADED = "Book: {} has been loaded";
    public static final String LOADED_ALL = "All {} books has been loaded";
    public static final String CREATED = "New book was created: {} {}";
    public static final String UPDATED = "Book was updated: {} {}";
    public static final String DELETED = "Deleted book id: {}";

    private final BookService bookService;
    //    -----------
    private final AuthorService authorService;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    @Operation(summary = "getBook", description = "FIND BOOK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book is found", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Book is not found", content = @Content)})
    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long id) {
        BookDto bookDto = bookService.findBookDtoById(id);
//--------------------
        Long authorId = bookDto.getAuthorId(authorService.findAuthorDtoById(id));
        AuthorDto authorDtoById = authorService.findAuthorDtoById(authorId);
        bookDto.setAuthorSurname(authorDtoById.getSurname());
//---------------------
        log.info(LOADED, bookDto.getTitle());
        return bookDto;
    }

    @Operation(summary = "getAllBooks", description = "FIND LIST BOOKS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "BookList is found", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "BookList is not found", content = @Content)})
    @GetMapping
    public List<BookDto> getAllBooks() {
        List<BookDto> allBookDto = bookService.findAllBookDto();
        log.info(LOADED_ALL, allBookDto.size());
        return allBookDto;
    }

    @Operation(summary = "addBook", description = "ADD BOOK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book is created", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Book is not created", content = @Content)})
    @PostMapping
    public BookDto addBook(@RequestBody BookDto bookDto) {
        log.info(CREATED, bookDto.getTitle(), bookDto.getYear());
        return bookService.saveBookDto(bookDto);
    }

    @Operation(summary = "updateBook", description = "UPDATE BOOK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book is updated", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Books is not updated", content = @Content)})
    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        bookDto.setId(id);
        log.info(UPDATED, bookDto.getTitle(), bookDto.getYear());
        return bookService.updateBookDto(bookDto);
    }

    @Operation(summary = "deleteBook", description = "DELETE BOOK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book is deleted", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Book is not deleted", content = @Content)})
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        log.info(DELETED, id);
        bookService.deleteBookDtoById(id);
    }
}
