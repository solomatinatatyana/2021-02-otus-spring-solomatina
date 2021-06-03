package ru.otus.homework.rest.mappers;


import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.otus.homework.domain.Author;
import ru.otus.homework.rest.dto.AuthorDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toAuthorDto(Author author);

    @InheritInverseConfiguration
    Author toAuthor(AuthorDto authorDto);

    List<AuthorDto> toAuthorDtoList(List<Author> authorList);
}
