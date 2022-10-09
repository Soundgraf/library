package com.example.library.model.converter;

import com.example.library.model.dto.AuthorDto;
import com.example.library.model.entity.Author;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
    AuthorDto authorToDto(Author author);
    Author dtoToAuthor(AuthorDto authorDto);

    //  Можно в использовать другие имена полей, но с аннотацией @Mapping в маппере
}
