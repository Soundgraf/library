package com.example.library.model.converter;

import com.example.library.model.dto.BookDto;
import com.example.library.model.entity.Book;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    BookDto bookToDto(Book book);
    Book dtoToBook(BookDto bookDto);

    //  Можно в использовать другие имена полей, но с аннотацией @Mapping в маппере
}
