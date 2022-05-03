package ru.otus.homework.service.genres;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.repository.genre.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.homework.util.SleepUtil.sleepRandomly;

@Service
public class GenreServiceImpl implements GenreService{
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @HystrixCommand(commandKey="getGenreKey", fallbackMethod="buildFallbackGenreCreate")
    @Transactional
    @Override
    public void insertGenre(Genre genre) {
        sleepRandomly();
        genreRepository.saveAndFlush(genre);
    }

    @HystrixCommand(commandKey="getGenreKey", fallbackMethod="buildFallbackGenreUpdate")
    @Override
    public void updateGenreById(long id, Genre genre) {
        sleepRandomly();
        Genre genreToBeUpdated = getGenreById(id);
        genreToBeUpdated.setName(genre.getName());
        genreRepository.saveAndFlush(genreToBeUpdated);
    }

    @HystrixCommand(commandKey="getGenreKey", fallbackMethod="buildFallbackGenresById")
    @Override
    public Genre getGenreById(long id) {
        sleepRandomly();
        return genreRepository.findById(id).orElseThrow(()->new GenreException("Genre with id [" + id + "] not found"));
    }

    @HystrixCommand(commandKey="getGenreKey", fallbackMethod="buildFallbackGenres")
    @Override
    public Genre getGenreByName(String name) {
        sleepRandomly();
        return genreRepository.findByName(name).orElseThrow(()->new GenreException("Genre with name [" + name + "] not found"));

    }

    @HystrixCommand(commandKey="getGenreKey", fallbackMethod="buildFallbackAllGenres")
    @Override
    public List<Genre> getAllGenres() {
        sleepRandomly();
        return genreRepository.findAll();
    }

    @HystrixCommand(commandKey="getGenreKey", fallbackMethod="buildFallbackGenreDelete")
    @Transactional
    @Override
    public void deleteGenreById(long id) {
        sleepRandomly();
        genreRepository.deleteById(id);
    }

    public Genre buildFallbackGenres(String genreName) {
        Genre genre = new Genre();
        genre.setId(0L);
        genre.setName(genreName);
        return genre;
    }

    public Genre buildFallbackGenresById(long id) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName("N/A genre");
        return genre;
    }

    public List<Genre> buildFallbackAllGenres() {
        Genre genre = new Genre();
        genre.setId(0L);
        genre.setName("N/A genre");
        List<Genre> genres = new ArrayList<>();
        genres.add(genre);
        return genres;
    }

    public void buildFallbackGenreUpdate(long id, Genre genre) {
        System.out.println(genre.getName() + " not updated");
    }

    public void buildFallbackGenreCreate(Genre genre) {
        System.out.println(genre.getName() + " not created");
    }

    public void buildFallbackGenreDelete(long id) {
        System.out.println("genre with " + id + " not deleted");
    }
}
