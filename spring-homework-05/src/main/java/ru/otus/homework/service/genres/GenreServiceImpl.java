package ru.otus.homework.service.genres;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.genre.GenreDao;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.GenreException;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }


    @Override
    public void insertGenre(long id, String name) {
        genreDao.insert(new Genre(id, name));
    }

    @Override
    public Genre getGenreById(long id) {
        return genreDao.findById(id).orElseThrow(()->new GenreException("Genre with id [" + id + "] not found"));
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.findAll();
    }

    @Override
    public void deleteGenreById(long id) {
        genreDao.deleteById(id);
    }
}
