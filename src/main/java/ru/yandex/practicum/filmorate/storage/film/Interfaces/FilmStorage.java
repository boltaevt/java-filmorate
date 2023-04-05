package ru.yandex.practicum.filmorate.storage.film.Interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film getFilm(Long filmId);

    List<Film> getPopular(int count);

    List<Film> getAllFilms();
}
