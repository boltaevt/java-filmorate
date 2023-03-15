package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    List<Film> findAll();

    Film create(Film film);

    Film updateFilmInfo(Film film) throws ObjectNotFoundException;

    Film findFilm(long id) throws ObjectNotFoundException;
}
