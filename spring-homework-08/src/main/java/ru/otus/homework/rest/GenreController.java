package ru.otus.homework.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.rest.dto.GenreDto;
import ru.otus.homework.rest.mappers.GenreMapper;
import ru.otus.homework.service.genres.GenreService;

import java.util.List;

@Controller
public class GenreController {
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    public GenreController(GenreService genreService, GenreMapper genreMapper) {
        this.genreService = genreService;
        this.genreMapper = genreMapper;
    }

    @GetMapping(value = "/genre")
    public String getGenres(Model model){
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("genres", genres);
        return "genre-list";
    }

    @GetMapping(value = "/genre/{id}/edit")
    public String editGenre(@PathVariable("id") String id, Model model){
        Genre genre = genreService.getGenreById(id);
        model.addAttribute("genre", genre);
        return "genre-edit";
    }

    @PatchMapping(value = "/genre/{id}")
    public String saveGenre(@PathVariable("id") String id,
                            @ModelAttribute("genre") GenreDto genre){
        genreService.updateGenreById(id, genreMapper.toGenre(genre));
        return "redirect:/genre";
    }

    @PostMapping(value = "/genre")
    public String addGenre(@ModelAttribute("genre") GenreDto genre, Model model){
        genreService.createGenre(genreMapper.toGenre(genre));
        model.addAttribute("genre", genre);
        return "redirect:/genre";
    }

    @DeleteMapping(value = "/genre/{id}")
    public String deleteGenre(@PathVariable("id") String id){
        genreService.deleteGenreById(id);
        return "redirect:/genre";
    }

    @ExceptionHandler(GenreException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") GenreException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }

    /*@ExceptionHandler(GenreException.class)
    public ModelAndView handleInternalError(@ModelAttribute("ex") GenreException ex){
        return new ModelAndView("/error/500","error",ex.getMessage());
    }*/
}
