package ru.yandex.practicum.filmorate.storage.film.Interfaces;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {

        Mpa getById(Long id);

        List<Mpa> getAll();

        Mpa getFilmMpa(Long filmId);

}
