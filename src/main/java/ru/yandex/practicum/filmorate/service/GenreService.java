package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.Interfaces.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    private final GenreStorage genreDbStorage;

    public GenreService(GenreStorage genreStorage) {
        this.genreDbStorage = genreStorage;
    }

    public Genre getById(Long id) {
        if (genreDbStorage.getById(id) == null) {
            throw new ObjectNotFoundException("Not found");
        }
        return genreDbStorage.getById(id);
    }

    public List<Genre> getAll() {
        return genreDbStorage.getAll();
    }

}