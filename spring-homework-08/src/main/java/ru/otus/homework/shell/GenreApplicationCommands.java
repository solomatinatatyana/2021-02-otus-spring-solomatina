package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.genres.GenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class GenreApplicationCommands {
    private final GenreService genreService;

    private long id;
    private String name;

    @ShellMethod(value = "getting genre by id", key = {"getGenreById", "ggId"})
    public Genre getGenreById(@ShellOption long id){
        this.id = id;
        return genreService.getGenreById(id);
    }

    @ShellMethod(value = "get all genre", key = {"getAllGenres", "gag"})
    public Iterable<Genre> getAllGenres(){
        return genreService.getAllGenres();
    }

    @ShellMethod(value = "insert new genre", key = {"insertGenre", "ig"})
    public void insertGenre(@ShellOption long id,
                            @ShellOption String name) {
        this.id = id;
        this.name = name;
        genreService.insertGenre(id,name);
    }

    @ShellMethod(value = "delete genre by id", key = {"deleteGenreById", "dgId"})
    public void deleteGenreById(@ShellOption long id){
        genreService.deleteGenreById(id);
    }
}
