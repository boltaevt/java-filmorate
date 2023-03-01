package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmComparator;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLikeToFilm(long filmId, long userId) throws ObjectNotFoundException {
        filmStorage.findFilm(filmId).getFilmLikesSet().add(userId);
    }

    public void removeLikeFromFilm(long filmId, long userId) throws ObjectNotFoundException {
        if (!filmStorage.findFilm(filmId).getFilmLikesSet().contains(userId)) {
            throw new ObjectNotFoundException("The film in question does not have a LIKE from the user in question.");
        }
        filmStorage.findFilm(filmId).getFilmLikesSet().remove(userId);
    }

    public Film findFilmById(long id) throws ObjectNotFoundException {
        return filmStorage.findFilm(id);
    }

    public List<Film> showTenMostPopularFilms(Integer n) {
        List<Film> mostLikedFilmsAll = new ArrayList<>(filmStorage.findAll());
        FilmComparator filmComparator = new FilmComparator();
        mostLikedFilmsAll.sort(filmComparator);
        if (mostLikedFilmsAll.size() >= n - 1) {
            mostLikedFilmsAll.subList(n, mostLikedFilmsAll.size()).clear();
        }
        return mostLikedFilmsAll;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film updateFilmInfo(Film film) throws ObjectNotFoundException {
        return filmStorage.updateFilmInfo(film);
    }
}
