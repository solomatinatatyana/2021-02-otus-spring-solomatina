package ru.otus.homework.rest.mappers;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.homework.domain.Book;
import ru.otus.homework.rest.dto.BookDto;

@Mapper(componentModel = "spring",uses = GenreMapper.class)
public interface BookMapper {

    @Mapping(source="book.comments",target = "comments", ignore=true)
    BookDto toBookDto(Book book);

    @InheritConfiguration
    Book toBook(BookDto bookDto);
}
