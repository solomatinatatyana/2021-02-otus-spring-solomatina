package ru.otus.homework.rest.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.rest.dto.GenreDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface GenreMapper {

    //@Mapping(source="genre.books",target = "books", ignore=true)
    GenreDto toGenreDto(Genre genre);

    @InheritInverseConfiguration
    Genre toGenre(GenreDto genreDto);

    List<GenreDto> toGenreDtoList(List<Genre> genreList);

    List<Genre> toGenreList(List<GenreDto> genreDtoList);
}
