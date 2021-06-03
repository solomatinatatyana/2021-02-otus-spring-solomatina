package ru.otus.homework.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.rest.mappers.GenreMapper;
import ru.otus.homework.service.genres.GenreService;

@Controller
public class GenrePagesController {
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    public GenrePagesController(GenreService genreService, GenreMapper genreMapper) {
        this.genreService = genreService;
        this.genreMapper = genreMapper;
    }

    @GetMapping(value = "/genre")
    public String getGenres(Model model){
        model.addAttribute("genres", "genres");
        return "genre-list";
    }

    @GetMapping(value = "/genre/{id}/edit")
    public String editGenre(@PathVariable("id") String id, Model model){
        Genre genre = genreService.getGenreById(id);
        model.addAttribute("genre", genre);
        return "genre-edit";
    }
}
