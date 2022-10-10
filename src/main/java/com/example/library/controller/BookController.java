package com.example.library.controller;

import com.example.library.model.converter.AuthorMapper;
import com.example.library.model.converter.BookMapper;
import com.example.library.model.dto.AuthorDto;
import com.example.library.model.dto.BookDto;
import com.example.library.repository.BookRepository;
import com.example.library.service.abstracts.AuthorService;
import com.example.library.service.abstracts.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public static final String NOT_FOUND = "Not Found ID: {}";
    public static final String NOT_FOUND_LIST = "List is empty";
    public static final String BAD_REQUEST_FOR_CREATE = "Bad Request for create: {}";
    public static final String BAD_REQUEST_FOR_UPDATE = "Bad Request for update: {}";
    public static final String BAD_REQUEST_FOR_DELETE = "Bad Request for delete with ID: {}";

    private final BookService bookService;

    @Operation(summary = "getBook", description = "FIND BOOK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book is found", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Book is not found", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        if (!bookService.existsBookById(id)) {
            log.info(NOT_FOUND, id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookDto bookDto = bookService.findBookDtoById(id);
        log.info(LOADED, bookDto.getTitle());
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @Operation(summary = "getAllBooks", description = "FIND LIST BOOKS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "BookList is found", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "BookList is not found", content = @Content)})
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> allBookDto = bookService.findAllBookDto();
        if (allBookDto.size() == 0) {
            log.info(NOT_FOUND_LIST);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info(LOADED_ALL, allBookDto.size());
        return new ResponseEntity<>(allBookDto, HttpStatus.OK);
    }

    @Operation(summary = "addBook", description = "ADD BOOK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book is created", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Book is not created", content = @Content)})
    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        if (bookDto.getTitle() == null || bookDto.getYear() == 0) {
            log.info(BAD_REQUEST_FOR_CREATE, bookDto);
            return new ResponseEntity<>(bookDto, HttpStatus.BAD_REQUEST);
        }
        log.info(CREATED, bookDto.getTitle(), bookDto.getYear());
        return new ResponseEntity<>(bookService.saveBookDto(bookDto), HttpStatus.OK);
    }

    @Operation(summary = "updateBook", description = "UPDATE BOOK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book is updated", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Books is not updated", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        if (!bookService.existsBookById(id) || bookDto.getTitle() == null || bookDto.getYear() == 0) {
            log.info(BAD_REQUEST_FOR_UPDATE, bookDto);
            return new ResponseEntity<>(bookDto, HttpStatus.BAD_REQUEST);
        }
        bookDto.setId(id);
        log.info(UPDATED, bookDto.getTitle(), bookDto.getYear());
        return new ResponseEntity<>(bookService.updateBookDto(bookDto), HttpStatus.OK);
    }

    @Operation(summary = "deleteBook", description = "DELETE BOOK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book is deleted", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Book is not deleted", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        if (!bookService.existsBookById(id)) {
            log.info(BAD_REQUEST_FOR_DELETE, id);
            return new ResponseEntity<>("Not found ID for delete", HttpStatus.BAD_REQUEST);
        }
        log.info(DELETED, id);
        bookService.deleteBookDtoById(id);
        return new ResponseEntity<>("Book with ID: " + id.toString() + " deleted", HttpStatus.OK);
    }
}
