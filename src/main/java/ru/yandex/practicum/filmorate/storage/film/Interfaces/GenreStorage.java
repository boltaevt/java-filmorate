package ru.yandex.practicum.filmorate.storage.film.Interfaces;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreStorage {

        Genre getById(Long id);

        List<Genre> getAll();

        Set<Genre> getFilmGenre(Long filmId);
}
