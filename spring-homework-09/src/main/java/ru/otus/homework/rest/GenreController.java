package ru.otus.homework.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.rest.dto.GenreDto;
import ru.otus.homework.rest.mappers.GenreMapper;
import ru.otus.homework.service.genres.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenreController {
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    public GenreController(GenreService genreService, GenreMapper genreMapper) {
        this.genreService = genreService;
        this.genreMapper = genreMapper;
    }

    @GetMapping(value = "/api/genre")
    public List<GenreDto> getGenres(){
        return genreService.getAllGenres().stream().map(genreMapper::toGenreDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/api/genre/{id}/edit")
    public ResponseEntity<GenreDto> editGenre(@PathVariable("id") String id){
        Genre genre = genreService.getGenreById(id);
        if(genre == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(genreMapper.toGenreDto(genre), HttpStatus.OK);
    }

    @PatchMapping(value = "/api/genre/{id}")
    public ResponseEntity<GenreDto> saveGenre(@PathVariable("id") String id,
                                            @RequestBody GenreDto genre){
        genreService.updateGenreById(id, genreMapper.toGenre(genre));
        return ResponseEntity.ok(genre);
    }

    @PostMapping(value = "/api/genre")
    public ResponseEntity<GenreDto> addGenre(@RequestBody GenreDto genre){
        genreService.createGenre(genreMapper.toGenre(genre));
        return ResponseEntity.ok(genre);
    }

    @DeleteMapping(value = "/api/genre/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable("id") String id){
        genreService.deleteGenreById(id);
        return ResponseEntity.ok("genre with id ["+ id +"] deleted!");
    }

    @ExceptionHandler(GenreException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") GenreException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }
}
