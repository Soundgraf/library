package com.example.library.controller;

import com.example.library.model.dto.AuthorDto;
import com.example.library.service.abstracts.AuthorService;
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

import java.net.http.HttpResponse;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Author RestController")
public class AuthorController {
    public static final String LOADED = "Author: {} has been loaded";
    public static final String LOADED_ALL = "All {} authors has been loaded";
    public static final String CREATED = "New author was created: {} {}";
    public static final String UPDATED = "Author was updated: {} {}";
    public static final String DELETED = "Deleted author id: {}";
    public static final String NOT_FOUND = "Not Found ID: {}";
    public static final String NOT_FOUND_LIST = "List authors is empty";
    public static final String BAD_REQUEST_FOR_CREATE = "Bad Request for create: {}";
    public static final String BAD_REQUEST_FOR_UPDATE = "Bad Request for update: {}";
    public static final String BAD_REQUEST_FOR_DELETE = "Bad Request for delete author with ID: {}";

    private final AuthorService authorService;

    @Operation(summary = "getAuthor", description = "FIND AUTHOR")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author is found", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Author is not found", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable Long id) {
        if (!authorService.existsAuthorById(id)) {
            log.info(NOT_FOUND, authorService.existsAuthorById(id));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorDto authorDto = authorService.findAuthorDtoById(id);
        log.info(LOADED, authorDto.getSurname());
        return new ResponseEntity<>(authorDto, HttpStatus.OK);
    }

    @Operation(summary = "getAllAuthors", description = "FIND LIST AUTHORS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AuthorList is found", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "AuthorList is not found", content = @Content)})
    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorDto> allAuthorDto = authorService.findAllAuthorDto();
        if (allAuthorDto.size() == 0) {
            log.info(NOT_FOUND_LIST);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info(LOADED_ALL, allAuthorDto.size());
        return new ResponseEntity<>(allAuthorDto, HttpStatus.OK);
    }

    @Operation(summary = "addAuthor", description = "ADD AUTHOR")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author is created", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Author is not created", content = @Content)})
    @PostMapping
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto authorDto) {
        if (authorDto.getName() == null || authorDto.getSurname() == null) {
            log.info(BAD_REQUEST_FOR_CREATE, authorDto);
            return new ResponseEntity<>(authorDto, HttpStatus.BAD_REQUEST);
        }
        log.info(CREATED, authorDto.getName(), authorDto.getSurname());
        return new ResponseEntity<>(authorService.saveAuthorDto(authorDto), HttpStatus.OK);
    }

    @Operation(summary = "updateAuthor", description = "UPDATE AUTHOR")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author is updated", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Author is not updated", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        authorDto.setId(id);
        if (!authorService.existsAuthorById(id) || authorDto.getName() == null || authorDto.getSurname() == null) {
            log.info(BAD_REQUEST_FOR_UPDATE, authorDto);
            return new ResponseEntity<>(authorDto, HttpStatus.BAD_REQUEST);
        }
        log.info(UPDATED, authorDto.getName(), authorDto.getSurname());
        return new ResponseEntity<>(authorService.saveAuthorDto(authorDto), HttpStatus.OK);
    }

    @Operation(summary = "deleteAuthor", description = "DELETE AUTHOR")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author is deleted", content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Author is not deleted", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        if (!authorService.existsAuthorById(id)) {
            log.info(BAD_REQUEST_FOR_DELETE, id);
            return new ResponseEntity<>("Not found ID for delete", HttpStatus.BAD_REQUEST);
        }
        log.info(DELETED, id);
        authorService.deleteAuthorDtoById(id);
        return new ResponseEntity<>("Author with ID: " + id.toString() + " deleted", HttpStatus.OK);
    }
}
