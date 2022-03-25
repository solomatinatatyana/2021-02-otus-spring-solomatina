package ru.otus.homework.service.genres;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.GenreException;
import ru.otus.homework.repository.genre.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    @Override
    public void insertGenre(Genre genre) {
        genreRepository.saveAndFlush(genre);
    }

    @Override
    public void updateGenreById(long id, Genre genre) {
        Genre genreToBeUpdated = getGenreById(id);
        genreToBeUpdated.setName(genre.getName());
        genreRepository.saveAndFlush(genreToBeUpdated);
    }

    @Override
    public Genre getGenreById(long id) {
        return genreRepository.findById(id).orElseThrow(()->new GenreException("Genre with id [" + id + "] not found"));
    }

    @Override
    public Genre getGenreByName(String name) {
        return genreRepository.findByName(name).orElseThrow(()->new GenreException("Genre with name [" + name + "] not found"));

    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteGenreById(long id) {
        genreRepository.deleteById(id);
    }
}
