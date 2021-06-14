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
    public void insertGenre(long id, String name) {
        genreRepository.save(new Genre(id, name));
    }

    @Override
    public Genre getGenreById(long id) {
        return genreRepository.findById(id).orElseThrow(()->new GenreException("Genre with id [" + id + "] not found"));
    }

    @Override
    public Iterable<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteGenreById(long id) {
        genreRepository.deleteById(id);
    }
}
