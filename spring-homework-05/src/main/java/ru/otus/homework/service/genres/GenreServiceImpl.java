package ru.otus.homework.service.genres;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.repository.genre.GenreRepositoryJpa;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.GenreException;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{
    private final GenreRepositoryJpa genreRepositoryJpa;

    public GenreServiceImpl(GenreRepositoryJpa genreRepositoryJpa) {
        this.genreRepositoryJpa = genreRepositoryJpa;
    }


    @Transactional
    @Override
    public void insertGenre(long id, String name) {
        genreRepositoryJpa.insert(new Genre(id, name));
    }

    @Override
    public Genre getGenreById(long id) {
        return genreRepositoryJpa.findById(id).orElseThrow(()->new GenreException("Genre with id [" + id + "] not found"));
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepositoryJpa.findAll();
    }

    @Transactional
    @Override
    public void deleteGenreById(long id) {
        genreRepositoryJpa.deleteById(id);
    }
}
